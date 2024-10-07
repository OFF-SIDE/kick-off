package offside.stadium.service;

import jakarta.transaction.Transactional;
import offside.StadiumCategoryEnum;
import offside.response.exception.CustomException;
import offside.response.exception.CustomExceptionTypes;
import offside.stadium.apiTypes.*;
import offside.stadium.domain.Stadium;
import offside.stadium.domain.StadiumInfo;
import offside.stadium.domain.StadiumRating;
import offside.stadium.domain.StadiumStar;
import offside.stadium.dto.StadiumWithInfoAndRatingAndStar;
import offside.stadium.dto.StadiumWithStarDto;
import offside.stadium.repository.StadiumInfoRepository;
import offside.stadium.repository.StadiumRatingRepository;
import offside.stadium.repository.StadiumRepository;
import offside.stadium.repository.StadiumStarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StadiumService {
    private final StadiumRepository stadiumRepository;
    private final StadiumInfoRepository stadiumInfoRepository;
    private final StadiumRatingRepository stadiumRatingRepository;
    private final StadiumStarRepository stadiumStarRepository;
    @Autowired
    public StadiumService(StadiumRepository stadiumRepository, StadiumInfoRepository stadiumInfoRepository, StadiumRatingRepository stadiumRatingRepository, StadiumStarRepository stadiumStarRepository) {
        this.stadiumRepository = stadiumRepository;
        this.stadiumInfoRepository = stadiumInfoRepository;
        this.stadiumRatingRepository = stadiumRatingRepository;
        this.stadiumStarRepository = stadiumStarRepository;
    }
    
    
    /**
      * @summary 구장과 해당 구장 정보를 최신화하는 함수
      * @param stadiumData
      * @param stadiumInfoDtoList
      */
    public void createNewStadiumByCrawler(CreateStadiumDto stadiumData, List<CreateStadiumInfoDto> stadiumInfoDtoList){
         final var stadium = stadiumRepository.findByXAndYAndCategory(stadiumData.getX(), stadiumData.getY(), stadiumData.getCategory()); // 구장 이미 있나 확인
         if(stadium.isEmpty()){ // 없으면 -> 새로 생성
             final var newStadium = stadiumRepository.save(new Stadium(stadiumData));
             final var stadiumInfoList = stadiumInfoDtoList.stream().map((stadiumInfoDto) -> new StadiumInfo(newStadium.getId(), stadiumInfoDto)).toList();
             stadiumInfoRepository.saveAll(stadiumInfoList);
         } else { // 있으면 -> 기존 Info 다 지우고 업데이트
             stadiumInfoRepository.deleteAllByStadiumId(stadium.get().getId());
             final var stadiumInfoList = stadiumInfoDtoList.stream().map((stadiumInfoDto) -> new StadiumInfo(stadium.get().getId(), stadiumInfoDto)).toList();
             stadiumInfoRepository.saveAll(stadiumInfoList);
         }
    }
//
    public List<StadiumWithStarDto> getStadiumListBySearch(NameSearchParamDto searchParamData){
        final var userId = searchParamData.getUserId();
        List<Stadium> stadiumList = stadiumRepository.findAllBySearchName(searchParamData.getSearchName());
        List<StadiumStar> starList = stadiumStarRepository.findAllByUserId(userId);
        return stadiumList.stream()
                .map(stadium -> new StadiumWithStarDto(stadium,
                        starList.contains(new StadiumStar(stadium.getId(), userId)) // 즐겨찾기 여부 확인
                ))
                .toList();
    }
    public List<StadiumWithStarDto> getStadiumListByCategoryAndRange(RangeSearchParamDto searchParamData){
        float startX = searchParamData.getStartX();
        float startY = searchParamData.getStartY();
        float endX = searchParamData.getEndX();
        float endY = searchParamData.getEndY();
        final var category = searchParamData.getCategory();
        final var userId = searchParamData.getUserId();

        List<Stadium> stadiumList = stadiumRepository.findAllBetweenLocationAndByCategory(category,startX,endX,startY,endY);
        List<StadiumStar> starList = stadiumStarRepository.findAllByUserId(userId);

        return stadiumList.stream()
                .map(stadium -> new StadiumWithStarDto(stadium,
                        starList.contains(new StadiumStar(stadium.getId(), userId)) // 즐겨찾기 여부 확인
                ))
                .toList();
    }

    public List<StadiumWithStarDto> getStadiumListByCategoryAndLocation(LocationSearchParamDto searchParamDto) {
        final var userId = searchParamDto.getUserId();
        List<Stadium> stadiumList = searchParamDto.getLocation().size() == 1
                ? stadiumRepository.findAllByCategoryAndLocation(searchParamDto.getCategory(), searchParamDto.getLocation().get(0))
                : stadiumRepository.findAllByCategoryAndLocationIn(searchParamDto.getCategory(), searchParamDto.getLocation());

        List<StadiumStar> starList = stadiumStarRepository.findAllByUserId(userId);

        return stadiumList.stream()
                .map(stadium -> new StadiumWithStarDto(stadium,
                        starList.contains(new StadiumStar(stadium.getId(), userId)) // 즐겨찾기 여부 확인
                ))
                .toList();
    }
    
    /**
     * 해당 구장을 평가하는 함수.
     * @param stadiumId
     * @param userId
     * @param rateStadiumDto
     * @return StadiumRating
     */
    public StadiumRating rateStadium(Integer stadiumId, Integer userId, RateStadiumDto rateStadiumDto){
        final var stadium = assertStadiumExist(stadiumId);
        
        stadiumRepository.updateRating(stadium.getId(), stadium.getTotalRating() + rateStadiumDto.rating, stadium.getRatingPeople() + 1);
        return stadiumRatingRepository.save(new StadiumRating(stadiumId, userId, rateStadiumDto));
    }
    
    public StadiumWithInfoAndRatingAndStar getStadiumInformation(Integer stadiumId, Integer userId){
        final var stadium = assertStadiumExist(stadiumId);
        
        final var stadiumInfoList = stadiumInfoRepository.findAllByStadiumId(stadiumId);
        final var stadiumRateList = stadiumRatingRepository.findAllByStadiumId(stadiumId);
        final var stadiumStar = stadiumStarRepository.findByUserIdAndStadiumId(userId, stadiumId);
        return new StadiumWithInfoAndRatingAndStar(stadium,stadiumInfoList,stadiumRateList, !stadiumStar.isEmpty());
    }
    
    public StadiumStar starStadium(Integer userId, Integer stadiumId){
        assertStadiumExist(stadiumId);
        final var star = stadiumStarRepository.findByUserIdAndStadiumId(userId, stadiumId);
        if(star.isEmpty()){
            return stadiumStarRepository.save(new StadiumStar(userId, stadiumId));
        }
        return star.get();
    }
    
    public void unstarStadium(Integer userId, Integer stadiumId){
        assertStadiumExist(stadiumId);
        stadiumStarRepository.deleteByUserIdAndStadiumId(userId, stadiumId);
    }
    
    // 내가 좋아요한 구장 목록 : 1번구장-3.4점 , 3번구장-4.5점
    public List<Stadium> getStarStadiumList(Integer userId){
        final var starList = stadiumStarRepository.findAllByUserId(userId); // (userId, stadiumId) (1,3) (1,4)
        final var starStadiumIdList = starList.stream().map(star -> star.getStadiumId()).toList();
        
        return stadiumRepository.findAllByIdIn(starStadiumIdList);
    }
    
    /**
     * 해당하는 구장이 있는 지 확인하여 반환. 없으면 에러
     * @param stadiumId
     * @return Stadium
     * @throw STADIUM_NOT_FOUND
     */
    public Stadium assertStadiumExist(Integer stadiumId){
        final var stadium = stadiumRepository.findById(stadiumId);
        if(stadium.isEmpty()){
            throw new CustomException(CustomExceptionTypes.STADIUM_NOT_FOUND);
        }
        return stadium.get();
    }
}