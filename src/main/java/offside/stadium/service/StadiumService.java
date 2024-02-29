package offside.stadium.service;

import jakarta.transaction.Transactional;
import offside.stadium.apiTypes.CreateStadiumDto;
import offside.stadium.apiTypes.CreateStadiumInfoDto;
import offside.stadium.apiTypes.LocationSearchParamDto;
import offside.stadium.apiTypes.RateStadiumDto;
import offside.stadium.apiTypes.RangeSearchParamDto;
import offside.stadium.domain.Stadium;
import offside.stadium.domain.StadiumInfo;
import offside.stadium.domain.StadiumRating;
import offside.stadium.domain.StadiumStar;
import offside.stadium.dto.StadiumWithInfoAndRating;
import offside.stadium.dto.StadiumWithRatingDto;
import offside.stadium.repository.StadiumInfoRepository;
import offside.stadium.repository.StadiumRatingRepository;
import offside.stadium.repository.StadiumRepository;
import offside.stadium.repository.StadiumStarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Stadium> getStadiumListBySearch(String searchName){
        return stadiumRepository.findAllBySearchName(searchName);
    }
    public List<StadiumWithRatingDto> getStadiumListByCategoryAndRange(RangeSearchParamDto searchParamData){
        float startX = searchParamData.getStartX();
        float startY = searchParamData.getStartY();
        float endX = searchParamData.getEndX();
        float endY = searchParamData.getEndY();
        final var category = searchParamData.getCategory();

        final var StadiumList = stadiumRepository.findAllBetweenLocationAndByCategory(category,startX,endX,startY,endY);
        return StadiumList.stream().map(stadium -> {
            final var stadiumRatingList = stadiumRatingRepository.findAllByStadiumId(stadium.getId());
            return new StadiumWithRatingDto(stadium, stadiumRatingList);
        }).toList();
    }
    
    public List<StadiumWithRatingDto> getStadiumListByCategoryAndLocation(LocationSearchParamDto searchParamDto){
        final var stadiumList = stadiumRepository.findAllByCategoryAndLocation(searchParamDto.getCategory(), searchParamDto.getLocation());
        return stadiumList.stream().map(stadium -> {
            return new StadiumWithRatingDto(stadium, stadiumRatingRepository.findAllByStadiumId(stadium.getId()));
        }).toList();
    }
    
    public StadiumRating rateStadium(Integer stadiumId, RateStadiumDto rateStadiumDto){
        return stadiumRatingRepository.save(new StadiumRating(stadiumId, rateStadiumDto));
    }
    
    public StadiumWithInfoAndRating getStadiumInformation(Integer stadiumId){
        final var stadium = stadiumRepository.findById(stadiumId);
        if(stadium.isEmpty()){
            throw new IllegalArgumentException("해당하는 구장이 없습니다");
        }
        final var stadiumInfoList = stadiumInfoRepository.findAllByStadiumId(stadiumId);
        final var stadiumRateList = stadiumRatingRepository.findAllByStadiumId(stadiumId);
        return new StadiumWithInfoAndRating(stadium.get(),stadiumInfoList,stadiumRateList);
    }
    
    public StadiumStar starStadium(Integer userId, Integer stadiumId){
        final var star = stadiumStarRepository.findByUserIdAndStadiumId(userId, stadiumId);
        if(star.isEmpty()){
            return stadiumStarRepository.save(new StadiumStar(userId, stadiumId));
        }
        return star.get();
    }
    
    public void unstarStadium(Integer userId, Integer stadiumId){
        stadiumStarRepository.deleteByUserIdAndStadiumId(userId, stadiumId);
    }
}