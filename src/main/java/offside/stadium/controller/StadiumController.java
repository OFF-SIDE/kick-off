package offside.stadium.controller;

import java.util.List;
import offside.stadium.apiTypes.CreateStadiumByCrawlerDto;
import offside.stadium.apiTypes.LocationSearchParamDto;
import offside.stadium.apiTypes.RateStadiumDto;
import offside.stadium.apiTypes.RangeSearchParamDto;
import offside.stadium.apiTypes.UserRequestDto;
import offside.stadium.domain.Stadium;
import offside.stadium.domain.StadiumRating;
import offside.stadium.domain.StadiumStar;
import offside.stadium.dto.StadiumWithInfoAndRating;
import offside.stadium.dto.StadiumWithRatingDto;
import offside.stadium.repository.StadiumInfoRepository;
import offside.stadium.repository.StadiumRepository;
import offside.stadium.service.StadiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class StadiumController {
    private final StadiumService stadiumService;
    private final StadiumRepository stadiumRepository;
    private final StadiumInfoRepository stadiumInfoRepository;
    
    @Autowired
    public StadiumController( StadiumRepository stadiumRepository, StadiumInfoRepository stadiumInfoRepository, StadiumService stadiumService) {
        this.stadiumRepository = stadiumRepository;
        this.stadiumInfoRepository = stadiumInfoRepository;
        this.stadiumService = stadiumService;
    }
    
    @PostMapping("/stadium/crawler")
    @ResponseBody
    public String createStadiumByCrawler(@RequestBody List<CreateStadiumByCrawlerDto> stadiumList){
        stadiumList.forEach((stadium)-> {
            this.stadiumService.createNewStadiumByCrawler(stadium.stadium, stadium.stadiumInfoList);
        });
        
        return "약 " + stadiumList.size() + "건의 구장 데이터 입력이 성공했습니다.";
    }
    
    @GetMapping("stadium/range")
    @ResponseBody
    public List<StadiumWithRatingDto> getStadiumListByCategoryAndRange(RangeSearchParamDto searchParamData, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IllegalArgumentException(bindingResult.getFieldError().getDefaultMessage());
        }
        
        // 1. 해당 범위내에 category가 맞는 구장들 return (stadium만 가져오기)
        return stadiumService.getStadiumListByCategoryAndRange(searchParamData);
    }
    
    @GetMapping("stadium/location")
    @ResponseBody
    public List<StadiumWithRatingDto> getStadiumListByCategoryAndLocation(LocationSearchParamDto searchParamData, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IllegalArgumentException(bindingResult.getFieldError().getDefaultMessage());
        }
        // 1. 해당 지역구에 category가 맞는 구장들 return (stadium만 가져오기)
        return stadiumService.getStadiumListByCategoryAndLocation(searchParamData);
    }

    @GetMapping("stadium/search")
    @ResponseBody
    public List<Stadium> getStadiumListBySearchName(@RequestParam("name") String searchName){
        // 1. 해당 이름이 포함되어 있는 구장이 있는지 return (stadium만 가져오기)
        return stadiumService.getStadiumListBySearch(searchName);
        // 이름이 "포함"되어 있는 구장은 어떻게 찾는가?
    }
    
    @PostMapping("stadium/{stadiumId}/rating")
    @ResponseBody
    public StadiumRating rateStadium(@PathVariable("stadiumId") Integer stadiumId, @RequestBody RateStadiumDto rateStadiumDto){
        // 0. 해당 유저가 로그인되어 있는지 확인
        
        // 해당 유저가 해당 구장에 평점 남기기
        final var stadiumRating = stadiumService.rateStadium(stadiumId, rateStadiumDto);
        return stadiumRating;
    }
    
    //구장 상세보기
    @GetMapping("stadium/{stadiumId}")
    @ResponseBody
    public StadiumWithInfoAndRating getStadiumInformation(@PathVariable("stadiumId") Integer stadiumId) {
        return stadiumService.getStadiumInformation(stadiumId);
    }

    @PostMapping("stadium/{stadiumId}/star")
    @ResponseBody
    public String starStadium(@PathVariable("stadiumId") Integer stadiumId, @RequestBody UserRequestDto userData){
        // star를 누르는 함수 -> star가 눌러지는 결과
        final var star = stadiumService.starStadium(userData.getUserId(), stadiumId);
        return "구장에 즐겨찾기가 되었습니다.";
    }
    
    @PostMapping("stadium/{stadiumId}/unstar")
    @ResponseBody
    public String unstarStadium(@PathVariable("stadiumId") Integer stadiumId, @RequestBody UserRequestDto userData){
        // star를 제거하는 함수 -> star가 제거되는 결과
        stadiumService.unstarStadium(userData.getUserId(), stadiumId);
        return "구장 즐겨찾기가 해제되었습니다.";
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
