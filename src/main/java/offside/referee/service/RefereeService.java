package offside.referee.service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import offside.LocationEnum;
import offside.referee.apiTypes.ChangeStatusDto;
import offside.referee.apiTypes.CreateRefereeHiringDto;
import offside.referee.apiTypes.CreateRefereeJiwonDto;
import offside.referee.apiTypes.RefereeSearchDto;
import offside.referee.domain.Referee;
import offside.referee.domain.RefereeLocation;
import offside.referee.domain.RefereeStar;
import offside.referee.dto.RefereeSummaryDto;
import offside.referee.repository.RefereeLocationRepository;
import offside.referee.repository.RefereeRatingRepository;
import offside.referee.repository.RefereeRepository;
import offside.referee.repository.RefereeStarRepository;
import offside.response.exception.CustomException;
import offside.response.exception.CustomExceptionTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        final var refereeLocationList = locationList.stream().map(location -> new RefereeLocation(refereeId, location)).toList();
        refereeLocationRepository.saveAll(refereeLocationList);
    }
    
    /**
     * 구인글 작성
     * @param userId
     * @param createRefereeHiringDto
     * @return
     */
    public Referee refereeHiring(Integer userId, CreateRefereeHiringDto createRefereeHiringDto){
        // 1. referee 구인 글 작성
        final var referee = refereeRepository.save(new Referee(userId,createRefereeHiringDto));
        // 2. referee 글 관련 entity 추가
        saveRefereeLocation(referee.getId(), createRefereeHiringDto.getLocation());
        return referee;
    }
    
    /**
     * 지원글 작성
     * @param userId
     * @param createRefereeJiwonDto
     * @return
     */
    public Referee refereeJiwon(Integer userId, CreateRefereeJiwonDto createRefereeJiwonDto){
        final var referee = refereeRepository.save(new Referee(userId, createRefereeJiwonDto));
        saveAllRefereeLocation(referee.getId(), createRefereeJiwonDto.getLocationList());
        return referee;
    }
    
    /**
     * 글 상태 변경하기. 본인 글만 가능
     * @param refereeId
     * @param userId
     * @param newStatus
     * @return Referee
     * @throw REFEREE_NOT_FOUND, REFEREE_UNAUTHORIZED
     */
    public Referee changeRefereeStatus(Integer refereeId, Integer userId,ChangeStatusDto newStatus){
        final var referee = refereeRepository.findById(refereeId);
        if(referee.isEmpty()){
            throw new CustomException(CustomExceptionTypes.REFEREE_NOT_FOUND);
        }
        if(referee.get().getUserId() != userId){
            throw new CustomException(CustomExceptionTypes.REFEREE_UNAUTHORIZED);
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
    public void refereeStar(Integer userId, Integer refereeId){
        assertRefereeExist(refereeId);
        final var star = refereeStarRepository.findByUserIdAndRefereeId(userId, refereeId);
        if(star.isEmpty()){
            refereeStarRepository.save(new RefereeStar(userId, refereeId));
        }
    }

    public void deleteRefereeStar(Integer userId, Integer refereeId){
        assertRefereeExist(refereeId);
        refereeStarRepository.deleteByUserIdAndRefereeId(userId, refereeId);
    }
    
    /**
     * 심판 스크랩 목록 가져오기.
     * @param userId
     * @param isHiring
     * @return
     */
    public List<Referee> getStarRefereeList(Integer userId, Boolean isHiring){
        final var staredRefereeList = this.refereeStarRepository.getAllStaredReferee(userId, isHiring);
        return staredRefereeList.stream().map(staredReferee -> (Referee) staredReferee[1]).toList();
    }
    
    public List<Referee> miriboki(Boolean isHiring){
        return refereeRepository.findTop3ByIsHiringOrderByCreatedAtDesc(isHiring);
    }

    public Referee assertRefereeExist(Integer refereeId){
        final var referee = refereeRepository.findById(refereeId);
        if(referee.isEmpty()){
            throw new CustomException(CustomExceptionTypes.REFEREE_NOT_FOUND);
        }
        return referee.get();
    }
}
