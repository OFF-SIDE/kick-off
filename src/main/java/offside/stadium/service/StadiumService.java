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
import java.util.Optional;

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
          * @param stadium
          * @param stadiumInfoDtoList
          */
    public void createNewStadiumByCrawler(CreateStadiumDto stadium, List<CreateStadiumInfoDto> stadiumInfoDtoList){
        // 1. 이미 있을 수도 있음
        // 1-a 있으면
        // final var existStadium = stadiumRepository.findByXandY();
        // if(existStadium.isEmpty()) -> 구장 삽입
        // else
        //      -> 있는 거 Id 쓰기
        //      -> 기존 stadiumInfo 들 싹다 지우기
        
        // 1-b 없으면
        final var createdStadium = stadiumRepository.save(new Stadium(stadium));
        
        List<StadiumInfo> stadiumInfoList = stadiumInfoDtoList.stream().map((stadiumInfoDto) -> new StadiumInfo(createdStadium.getId(), stadiumInfoDto)).toList();
        stadiumInfoRepository.saveAll(stadiumInfoList);
    }
//
    public List<Stadium> getStadiumListBySearch(String searchName){
        return stadiumRepository.findEntitiesBySearchName(searchName);
    }
    public List<Stadium> getStadiumListByCategoryAndLocation(SearchParamDto searchParamData){
        float startX = searchParamData.getStartX();
        float startY = searchParamData.getStartY();
        float endX = searchParamData.getEndX();
        float endY = searchParamData.getEndY();
        String category = searchParamData.getCategory();
    
        System.out.println(category);
        System.out.println(startX);
        System.out.println(startY);
        System.out.println(endX);
        System.out.println(endY);
        
        return stadiumRepository.findEntitiesBetweenValues(category,startX,endX,startY,endY);
    }
//
//    public Optional<Stadium> gotoStadiumInformation(Integer stadiumId){
//        return stadiumRepository.findById(stadiumId);
//    }
}