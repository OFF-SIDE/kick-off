package offside.stadium.controller;

import java.util.List;
import offside.stadium.dto.CreateStadiumByCrawlerDto;
import offside.stadium.dto.LocationRangeDto;
import offside.stadium.service.StadiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller()
public class StadiumController {
    private final StadiumService stadiumService;

    @Autowired
    public StadiumController(StadiumService stadiumService) {
        this.stadiumService = stadiumService;
    }
    
    @PostMapping("/stadium")
    @ResponseBody
    public CreateStadiumByCrawlerDto createStadiumByCrawler(@RequestBody() List<CreateStadiumByCrawlerDto> stadiumList){
        // 1. stadiumList.stadium 이 이미 있는지 확인한다. (같은지 확인 = stadium 디비에 X,Y값을 비교하는 것)
        // 1-a. 만약 없다면 stadium을 삽입한다. -> stadiumId 가져오기
        // 1-b, 있으면, 그대로 둔다. -> stadiumId 가져오기
        
        // 2. stadiumList. stadiumInfo 들을 삽입한다.
        // 2-a. 1-a였다면, 그냥 info를 다 삽입한다. (with 1-a에서 받아온 stadiumId와 함께)
        // 2-b. 1-b였다면, 기존 stadiumInfo을 다 삭제하고 새로 덮어씌운다
        
        return stadiumList.get(0);
    }
    
    @GetMapping("stadium")
    @ResponseBody
    public void getStadiumListByCategoryOrLocation(@RequestParam("category") String category, @RequestParam("location") LocationRangeDto locationRange){
        // 0. 유효성 검사
        // - category는 무조건 와야함
        // - locationRange도 무조건 와야함
        
        // 1. 해당 범위내에 category가 맞는 구장들 return (stadium만 가져오기)
    }
    
    @GetMapping("stadium/search")
    @ResponseBody
    public void getStadiumListBySearchName(@RequestParam("name") String searchName){
        // 1. 해당 이름으로 시작하는 구장이 있는지 return (stadium만 가져오기)
    }
    
    @GetMapping("stadium/{stadiumId}")
    @ResponseBody
    public void gotoStadiumInformation(@PathVariable("stadiumId") Integer stadiumId){
        // 1. 해당 구장 정보를 반환
        // - stadium 자체의 데이터
        // - stadiumInfo 에 해당하는 데이터
        // - stadium에 해당하는 평점(점수만)/방문자 수/즐겨찾기 수 (in stadium 테이블) (후순위 why? 유저 정보가 아직 없음)
    }
    
    @GetMapping("stadium/{stadiumId}/rating")
    @ResponseBody
    public void getStadiumRating(@PathVariable("stadiumId") Integer stadiumId){
        // 해당 구장의 평가 목록을 반환
        // - stadium_rating 테이블을 확인 (누가 몇점을 줬는지 세부 rating 정보 반환)(후순위 Why? 어느 유저가 평가 내린지 아직은 없음)
    }
    
    
    //////// 나중에 추가해도 되는 기능 //////////////
    @PostMapping("stadium/{stadiumId}/rating")
    @ResponseBody
    public void rateinStadium(){
        // 0. 해당 유저가 로그인되어 있는지 확인
        // 0. 해당 유저가 해당 구장을 이용했는지?
    }
    
    @PostMapping("stadium/{stadiumId}/star")
    @ResponseBody
    public void starinStadium(){
        // 0. 해당 유저가 로그인되어 있는지 확인
        // 1. 즐겨찾기로 이미 등록되어 있으면 삭제 / 없으면 추가
    }
    
}
