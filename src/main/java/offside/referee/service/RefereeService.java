package offside.referee.service;

import java.util.ArrayList;
import java.util.List;
import offside.CategoryEnum;
import offside.LocationEnum;
import offside.referee.apiTypes.ChangeStatusDto;
import offside.referee.apiTypes.CreateRefereeHiringDto;
import offside.referee.apiTypes.CreateRefereeJiwonDto;
import offside.referee.apiTypes.RefereeSearchDto;
import offside.referee.domain.Referee;
import offside.referee.domain.RefereeDate;
import offside.referee.domain.RefereeLocation;
import offside.referee.domain.RefereeTime;
import offside.referee.dto.RefereeSummaryDto;
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
    private final RefereeLocationRepository refereeLocationRepository;
    private final RefereeRatingRepository refereeRatingRepository;
    private final RefereeTimeRepository refereeTimeRepository;
    private final RefereeStarRepository refereeStarRepository;
    
    @Autowired
    public RefereeService(RefereeRepository refereeRepository,
        RefereeDateRepository refereeDateRepository,
        RefereeLocationRepository refereeLocationRepository,
        RefereeRatingRepository refereeRatingRepository,
        RefereeTimeRepository refereeTimeRepository,
        RefereeStarRepository refereeStarRepository) {
        this.refereeRepository = refereeRepository;
        this.refereeDateRepository = refereeDateRepository;
        this.refereeLocationRepository = refereeLocationRepository;
        this.refereeRatingRepository = refereeRatingRepository;
        this.refereeTimeRepository = refereeTimeRepository;
        this.refereeStarRepository = refereeStarRepository;
    }
    
    public void saveAllRefereeDate(Integer refereeId, List<Integer> dateList) {
        final var refereeDateList = dateList.stream().map(date-> {
            return new RefereeDate(refereeId, date);
        }).toList();
        refereeDateRepository.saveAll(refereeDateList);
    }
    
    public void saveAllRefereeTime(Integer refereeId, List<Integer> timeList) {
        final var refereeTimeList =  timeList.stream().map(time -> {
            return new RefereeTime(refereeId, time);
        }).toList();
        refereeTimeRepository.saveAll(refereeTimeList);
    }
    
    public void saveRefereeLocation(Integer refereeId, LocationEnum location) {
        refereeLocationRepository.save(new RefereeLocation(refereeId, location));
    }
    public void saveAllRefereeLocation(Integer refereeId, List<LocationEnum> locationList) {
        final var refereeLocationList = locationList.stream().map(location -> {
            return new RefereeLocation(refereeId, location);
        }).toList();
        refereeLocationRepository.saveAll(refereeLocationList);
    }
    
    public Referee refereeHiring(CreateRefereeHiringDto createRefereeHiringDto){
        // 1. referee 구인 글 작성
        final var referee = refereeRepository.save(new Referee(createRefereeHiringDto));
        
        // 2. referee 글 관련 entity 추가
        saveAllRefereeDate(referee.getId(), createRefereeHiringDto.getDateList());
        saveAllRefereeTime(referee.getId(), createRefereeHiringDto.getTimeList());
        saveRefereeLocation(referee.getId(), createRefereeHiringDto.getLocation());
        
        return referee;
    }
    
    public Referee refereeJiwon(CreateRefereeJiwonDto createRefereeJiwonDto){
        final var referee = refereeRepository.save(new Referee(createRefereeJiwonDto));
        
        saveAllRefereeDate(referee.getId(), createRefereeJiwonDto.getDateList());
        saveAllRefereeTime(referee.getId(), createRefereeJiwonDto.getTimeList());
        saveAllRefereeLocation(referee.getId(), createRefereeJiwonDto.getLocationList());
        
        return referee;
    }

    public Referee changeRefereeStatus(Integer refereeId, ChangeStatusDto newStatus){
        final var referee = refereeRepository.findById(refereeId);
        if(referee.isEmpty()){
            throw new IllegalArgumentException("해당하는 심판 글이 없습니다.");
        }
        referee.get().setStatus(newStatus.getStatus());
        return refereeRepository.save(referee.get());
    }
    
    
    // By isHiring, Location, dateList, timeList, category, status
    public List<RefereeSummaryDto> getRefereeListBySearchDto(RefereeSearchDto refereeSearchDto){
        // isHiring, status로 가져오기
        final var refereeList = refereeRepository.findAllByIsHiringAndCategoryAndStatus(refereeSearchDto.getIsHiring(),refereeSearchDto.getCategory(),refereeSearchDto.getStatus());
        // 1-, 3-, 6-,
        final var refereeIdList = refereeList.stream().map(referee -> referee.getId()).toList();
        
        final var refereeLocationList = refereeLocationRepository.findAllByRefereeIdInAndLocationIn(refereeIdList, refereeSearchDto.getLocationList());
        final var refereeIdListInLocationList = refereeLocationList.stream().map(refereeLocation -> refereeLocation.getRefereeId()).toList();
        
        // 1,3,6 애들중에 dateList를 지닌 애들만 가져오기
        final var refereeDateList= refereeDateRepository.findAllByRefereeIdInAndDateIn(refereeIdList, refereeSearchDto.getDateList());
        final var refereeIdListInDateList =  refereeDateList.stream().map(refereeDate -> refereeDate.getRefereeId()).toList();
        // 1번 심판이 1000 1030 1100 1130
        // 검색을 1100,1130,1200
        // 1,3,6 애들중에 timeList를 지닌 애들만 가져오기
        final var refereeTimeList= refereeTimeRepository.findAllByRefereeIdInAndHourIn(refereeIdList, refereeSearchDto.getTimeList());
        final var refereeIdListInTimeList =  refereeTimeList.stream().map(refereeTime -> refereeTime.getRefereeId()).toList();
        // 1-(), 2-() 3-() 4-() 5-()
        return refereeList.stream()
            .filter(referee -> {
                return refereeIdListInLocationList.contains(referee.getId()) && refereeIdListInDateList.contains(referee.getId()) && refereeIdListInTimeList.contains(referee.getId());
            }) // 3-() 4-()
            .map(referee -> { // 3-20240314 4-20240314, 3-2024031
                final var dateList = refereeDateList.stream().filter(refereeDate -> {
                    return refereeDate.getRefereeId() == referee.getId();
                }).map(refereeDate -> refereeDate.getDate()).toList(); // [20240314, 20240315]
                
                // 3-1100 3-1200 4-1000
                final var timeList = refereeTimeList.stream().filter(refereeTime -> {
                    return refereeTime.getRefereeId() == referee.getId();
                }).map(refereeTime -> refereeTime.getHour()).toList(); // [1100,1200]
                
                final var locationList = refereeLocationList.stream().filter(refereeLocation -> {
                    return refereeLocation.getRefereeId() == referee.getId();
                }).map(refereeLocation -> refereeLocation.getLocation()).toList();
                
                return new RefereeSummaryDto(referee, locationList, dateList, timeList);
            }).toList();
    }
}
