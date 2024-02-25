package offside.stadium.controller;

import java.util.List;
import offside.stadium.apiTypes.CreateStadiumByCrawlerDto;
import offside.stadium.apiTypes.SearchParamDto;
import offside.stadium.domain.Stadium;
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
    public String createStadiumByCrawler(@RequestBody() List<CreateStadiumByCrawlerDto> stadiumList){
        stadiumList.forEach((stadium)-> {
            this.stadiumService.createNewStadiumByCrawler(stadium.stadium, stadium.stadiumInfoList);
        });
        
        return "약 " + stadiumList.size() + "건의 구장 데이터 입력이 성공했습니다.";
    }
    
    @GetMapping("stadium")
    @ResponseBody
    public List<Stadium> getStadiumListByCategoryOrLocation(SearchParamDto searchParamData, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new IllegalArgumentException(bindingResult.getFieldError().getDefaultMessage());
        }
        
        // 1. 해당 범위내에 category가 맞는 구장들 return (stadium만 가져오기)
        return stadiumService.getStadiumListByCategoryAndLocation(searchParamData);
    }

    @GetMapping("stadium/search")
    @ResponseBody
    public List<Stadium> getStadiumListBySearchName(@RequestParam("name") String searchName){
        // 1. 해당 이름이 포함되어 있는 구장이 있는지 return (stadium만 가져오기)
        return stadiumService.getStadiumListBySearch(searchName);
        // 이름이 "포함"되어 있는 구장은 어떻게 찾는가?
    }
//
//    @GetMapping("stadium/{stadiumId}")
//    @ResponseBody
//    public Optional<Stadium> gotoStadiumInformation(@PathVariable("stadiumId") Integer stadiumId){
//        // 1. 해당 구장 정보를 반환
//        // - stadium 자체의 데이터
//        // - stadiumInfo 에 해당하는 데이터
//        // - stadium에 해당하는 평점(점수만)/방문자 수/즐겨찾기 수 (in stadium 테이블) (후순위 why? 유저 정보가 아직 없음)
//        return stadiumService.gotoStadiumInformation(stadiumId);
//    }
//
//    @GetMapping("stadium/{stadiumId}/rating")
//    @ResponseBody
//    public void getStadiumRating(@PathVariable("stadiumId") Integer stadiumId){
//        // 해당 구장의 평가 목록을 반환
//        // - stadium_rating 테이블을 확인 (누가 몇점을 줬는지 세부 rating 정보 반환)(후순위 Why? 어느 유저가 평가 내린지 아직은 없음)
//    }
//
//
//    //////// 나중에 추가해도 되는 기능 //////////////
//    @PostMapping("stadium/{stadiumId}/rating")
//    @ResponseBody
//    public void rateinStadium(){
//        // 0. 해당 유저가 로그인되어 있는지 확인
//        // 0. 해당 유저가 해당 구장을 이용했는지?
//    }
//
//    @PostMapping("stadium/{stadiumId}/star")
//    @ResponseBody
//    public void starinStadium(){
//        // 0. 해당 유저가 로그인되어 있는지 확인
//        // 1. 즐겨찾기로 이미 등록되어 있으면 삭제 / 없으면 추가
//    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
