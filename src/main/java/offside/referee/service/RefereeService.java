package offside.referee.service;

import java.util.List;
import offside.CategoryEnum;
import offside.LocationEnum;
import offside.StatusEnum;
import offside.referee.apiTypes.CreateRefereeHiringDto;
import offside.referee.apiTypes.CreateRefereeJiwonDto;
import offside.referee.domain.Referee;
import offside.referee.domain.RefereeCategory;
import offside.referee.domain.RefereeDate;
import offside.referee.domain.RefereeLocation;
import offside.referee.domain.RefereeTime;
import offside.referee.repository.RefereeCategoryRepository;
import offside.referee.repository.RefereeDateRepository;
import offside.referee.repository.RefereeLocationRepository;
import offside.referee.repository.RefereeRatingRepository;
import offside.referee.repository.RefereeRepository;
import offside.referee.repository.RefereeStarRepository;
import offside.referee.repository.RefereeTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefereeService {
    
    private final RefereeRepository refereeRepository;
    private final RefereeDateRepository refereeDateRepository;
    private final RefereeCategoryRepository refereeCategoryRepository;
    private final RefereeLocationRepository refereeLocationRepository;
    private final RefereeRatingRepository refereeRatingRepository;
    private final RefereeTimeRepository refereeTimeRepository;
    private final RefereeStarRepository refereeStarRepository;
    
    @Autowired
    public RefereeService(RefereeRepository refereeRepository,
        RefereeDateRepository refereeDateRepository,
        RefereeCategoryRepository refereeCategoryRepository,
        RefereeLocationRepository refereeLocationRepository,
        RefereeRatingRepository refereeRatingRepository,
        RefereeTimeRepository refereeTimeRepository,
        RefereeStarRepository refereeStarRepository) {
        this.refereeRepository = refereeRepository;
        this.refereeDateRepository = refereeDateRepository;
        this.refereeCategoryRepository = refereeCategoryRepository;
        this.refereeLocationRepository = refereeLocationRepository;
        this.refereeRatingRepository = refereeRatingRepository;
        this.refereeTimeRepository = refereeTimeRepository;
        this.refereeStarRepository = refereeStarRepository;
    }
    
    public void saveRefereeDate(Integer refereeId, List<Integer> dateList) {
        final var refereeDateList = dateList.stream().map(date-> {
            return new RefereeDate(refereeId, date);
        }).toList();
        refereeDateRepository.saveAll(refereeDateList);
    }
    
    public void saveRefereeTime(Integer refereeId, List<Integer> timeList) {
        final var refereeTimeList =  timeList.stream().map(time -> {
            return new RefereeTime(refereeId, time);
        }).toList();
        refereeTimeRepository.saveAll(refereeTimeList);
    }
    
    public void saveRefereeLocation(Integer refereeId, List<LocationEnum> locationList) {
        final var refereeLocationList = locationList.stream().map(location-> {
            return new RefereeLocation(refereeId, location);
        }).toList();
        refereeLocationRepository.saveAll(refereeLocationList);
    }
    
    public void saveRefereeCategory(Integer refereeId, List<CategoryEnum> categoryList) {
        final var refereeCategoryList = categoryList.stream().map(category-> {
            return new RefereeCategory(refereeId, category);
        }).toList();
        refereeCategoryRepository.saveAll(refereeCategoryList);
    }
    
    
    public Referee refereeHiring(CreateRefereeHiringDto createRefereeHiringDto){
        // 1. referee 구인 글 작성
        final var referee = refereeRepository.save(new Referee(createRefereeHiringDto));
        
        // 2. referee 글 관련 entity 추가
        saveRefereeDate(referee.getId(), createRefereeHiringDto.getDateList());
        saveRefereeTime(referee.getId(), createRefereeHiringDto.getTimeList());
        saveRefereeCategory(referee.getId(), createRefereeHiringDto.getCategoryList());
        saveRefereeLocation(referee.getId(), createRefereeHiringDto.getLocationList());
        
        return referee;
    }
    
    public Referee refereeJiwon(CreateRefereeJiwonDto createRefereeJiwonDto){
        final var referee = refereeRepository.save(new Referee(createRefereeJiwonDto));
        
        saveRefereeDate(referee.getId(), createRefereeJiwonDto.getDateList());
        saveRefereeTime(referee.getId(), createRefereeJiwonDto.getTimeList());
        saveRefereeCategory(referee.getId(), createRefereeJiwonDto.getCategoryList());
        saveRefereeLocation(referee.getId(), createRefereeJiwonDto.getLocationList());
        
        return referee;
    }

    public Referee changeStatus(Integer refereeId, StatusEnum newStatus){
        final var referee = refereeRepository.findByRefereeId(refereeId);
        referee.setStatus(newStatus);
        return referee;
    }
    
    
}
