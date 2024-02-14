package offside.stadium.service;

import jakarta.transaction.Transactional;

import offside.stadium.apiTypes.CreateStadiumByCrawlerDto;
import offside.stadium.apiTypes.CreateStadiumDto;
import offside.stadium.apiTypes.SearchParamDto;
import offside.stadium.domain.Stadium;
import offside.stadium.repository.StadiumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StadiumService {
    private final StadiumRepository stadiumRepository;
    @Autowired
    public StadiumService(StadiumRepository stadiumRepository) {
        this.stadiumRepository = stadiumRepository;
    }

    public void createNewStadium(CreateStadiumByCrawlerDto newStadium){
        Stadium stadium = new Stadium(newStadium.stadium);
        stadiumRepository.save(stadium);
    }

    public List<Stadium> getStadiumListBySearch(String searchName){
        return stadiumRepository.findAllByName(searchName);
    }
    public List<Stadium> getStadiumListByCategoryAndLocation(SearchParamDto searchParamData){
        float startX = searchParamData.getStartX();
        float startY = searchParamData.getStartY();
        float endX = searchParamData.getEndX();
        float endY = searchParamData.getEndY();
        String category = searchParamData.getCategory();
        return stadiumRepository.findEntitiesBetweenValues(category,startX,endX,startY,endY);
    }

    public Optional<Stadium> gotoStadiumInformation(Integer stadiumId){
        return stadiumRepository.findById(stadiumId);
    }
}