package offside.referee.service;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import offside.LocationEnum;
import offside.referee.apiTypes.ChangeStatusDto;
import offside.referee.apiTypes.CreateRefereeHiringDto;
import offside.referee.apiTypes.CreateRefereeJiwonDto;
import offside.referee.apiTypes.RefereeSearchDto;
import offside.referee.domain.Referee;
import offside.referee.domain.RefereeLocation;
import offside.referee.dto.RefereeSummaryDto;
import offside.referee.repository.RefereeLocationRepository;
import offside.referee.repository.RefereeRatingRepository;
import offside.referee.repository.RefereeRepository;
import offside.referee.repository.RefereeStarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefereeService {
    
    private final RefereeRepository refereeRepository;
    private final RefereeLocationRepository refereeLocationRepository;
    private final RefereeRatingRepository refereeRatingRepository;
    private final RefereeStarRepository refereeStarRepository;
    
    @Autowired
    public RefereeService(RefereeRepository refereeRepository,
        RefereeLocationRepository refereeLocationRepository,
        RefereeRatingRepository refereeRatingRepository,
        RefereeStarRepository refereeStarRepository) {
        this.refereeRepository = refereeRepository;
        this.refereeLocationRepository = refereeLocationRepository;
        this.refereeRatingRepository = refereeRatingRepository;
        this.refereeStarRepository = refereeStarRepository;
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
        saveRefereeLocation(referee.getId(), createRefereeHiringDto.getLocation());
        return referee;
    }
    
    public Referee refereeJiwon(CreateRefereeJiwonDto createRefereeJiwonDto){
        final var referee = refereeRepository.save(new Referee(createRefereeJiwonDto));
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

    // Q. 0315 ~ 0316 -------> 0314 ~ 0320   (q.startDate <= db.endDate && q.endDate >= db.startDate)
    public List<RefereeSummaryDto> getRefereeListBySearchDto(RefereeSearchDto refereeSearchDto){
        final var refereeList = refereeRepository.findAllBySearch(refereeSearchDto.getStartTime(), refereeSearchDto.getEndTime(), refereeSearchDto.getStartDate(), refereeSearchDto.getEndDate(), refereeSearchDto.getIsHiring(), refereeSearchDto.getCategory(), refereeSearchDto.getStatus());
        final var refereeIdList = refereeList.stream().map(referee -> referee.getId()).toList();
        final var refereeLocationList = refereeLocationRepository.findAllByRefereeIdInAndLocationIn(refereeIdList, refereeSearchDto.getLocationList());
        final var locationMap = new HashMap<Integer, List<LocationEnum>>();
    
        // 1,3,6,8->  3-마포구,3-성동구, 6-마포구, 6-성동구 -> 3-[마포구,성동구], 6-[마포구,성동구]
        refereeLocationList.stream().forEach(refereeLocation -> {
            final var locationList = locationMap.get(refereeLocation.getRefereeId());
            if(locationList == null){
                final var newLocationList = new ArrayList<LocationEnum>();
                newLocationList.add(refereeLocation.getLocation());
                locationMap.put(refereeLocation.getRefereeId(),newLocationList);
            }
            else{
                locationList.add(refereeLocation.getLocation());
                locationMap.put(refereeLocation.getRefereeId(),locationList); // 3-[마포구] -> 3-[마포구, 성동구]
            }
        });
        final var refereeLocationIdSet = locationMap.keySet();
        
        return refereeLocationIdSet.stream().map(refereeLocationId -> {
            final var referee = refereeList.stream().filter(ref -> ref.getId() == refereeLocationId).findFirst();
            return new RefereeSummaryDto(referee.get(), locationMap.get(refereeLocationId));
        }).toList();
    }
}
