package offside.stadium.service;

import jakarta.transaction.Transactional;

import offside.stadium.apiTypes.CreateStadiumDto;
import offside.stadium.apiTypes.CreateStadiumInfoDto;
import offside.stadium.apiTypes.SearchParamDto;
import offside.stadium.domain.Stadium;
import offside.stadium.domain.StadiumInfo;
import offside.stadium.repository.StadiumInfoRepository;
import offside.stadium.repository.StadiumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class StadiumService {
    private final StadiumRepository stadiumRepository;
    private final StadiumInfoRepository stadiumInfoRepository;
    @Autowired
    public StadiumService(StadiumRepository stadiumRepository, StadiumInfoRepository stadiumInfoRepository) {
        this.stadiumRepository = stadiumRepository;
        this.stadiumInfoRepository = stadiumInfoRepository;
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
    public List<Stadium> getStadiumListByCategoryAndLocation(SearchParamDto searchParamData){
        float startX = searchParamData.getStartX();
        float startY = searchParamData.getStartY();
        float endX = searchParamData.getEndX();
        float endY = searchParamData.getEndY();
        final var category = searchParamData.getCategory();

        return stadiumRepository.findAllBetweenLocationAndByCategory(category,startX,endX,startY,endY);
    }
}