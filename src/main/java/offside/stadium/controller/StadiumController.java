package offside.stadium.controller;

import jakarta.validation.Valid;
import java.util.List;
import offside.response.ApiResponse;
import offside.response.ValidationException;
import offside.stadium.apiTypes.CreateStadiumByCrawlerDto;
import offside.stadium.apiTypes.LocationSearchParamDto;
import offside.stadium.apiTypes.RateStadiumDto;
import offside.stadium.apiTypes.RangeSearchParamDto;
import offside.stadium.apiTypes.UserRequestDto;
import offside.stadium.domain.Stadium;
import offside.stadium.domain.StadiumRating;
import offside.stadium.dto.StadiumWithInfoAndRatingAndStar;
import offside.stadium.service.StadiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("stadium")
public class StadiumController {
    private final StadiumService stadiumService;
    
    @Autowired
    public StadiumController(StadiumService stadiumService) {
        this.stadiumService = stadiumService;
    }
    
    @PostMapping("crawler")
    @ResponseBody
    public ApiResponse createStadiumByCrawler(@RequestBody List<CreateStadiumByCrawlerDto> stadiumList){
        stadiumList.forEach((stadium)-> {
            this.stadiumService.createNewStadiumByCrawler(stadium.stadium, stadium.stadiumInfoList);
        });
        
        return ApiResponse.createSuccess("약 " + stadiumList.size() + "건의 구장 데이터 입력이 성공했습니다.");
    }
    
    @GetMapping("range")
    @ResponseBody
    public ApiResponse<List<Stadium>> getStadiumListByCategoryAndRange(@Valid RangeSearchParamDto searchParamData, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult);
        }
        return ApiResponse.createSuccess(stadiumService.getStadiumListByCategoryAndRange(searchParamData));
    }
    
    @GetMapping("location")
    @ResponseBody
    public ApiResponse<List<Stadium>> getStadiumListByCategoryAndLocation(@Valid LocationSearchParamDto searchParamData, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult);
        }
        // 1. 해당 지역구에 category가 맞는 구장들 return (stadium만 가져오기)
        return ApiResponse.createSuccess(stadiumService.getStadiumListByCategoryAndLocation(searchParamData));
    }

    @GetMapping("search")
    @ResponseBody
    public ApiResponse<List<Stadium>> getStadiumListBySearchName(@RequestParam("name") String searchName){
        // 1. 해당 이름이 포함되어 있는 구장이 있는지 return (stadium만 가져오기)
        return ApiResponse.createSuccess(stadiumService.getStadiumListBySearch(searchName));
    }
    
    @PostMapping("{stadiumId}/rating")
    @ResponseBody
    public ApiResponse<StadiumRating> rateStadium(@PathVariable("stadiumId") Integer stadiumId, @Valid @RequestBody RateStadiumDto rateStadiumDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult);
        }
        // 0. 해당 유저가 로그인되어 있는지 확인
        return ApiResponse.createSuccess(stadiumService.rateStadium(stadiumId, rateStadiumDto));
    }

    //구장 상세보기
    @GetMapping("{stadiumId}")
    @ResponseBody
    public ApiResponse<StadiumWithInfoAndRatingAndStar> getStadiumInformation(@PathVariable("stadiumId") Integer stadiumId,@RequestParam("userId") Integer userId) {
        return ApiResponse.createSuccess(stadiumService.getStadiumInformation(stadiumId, userId));
    }

    @PostMapping("{stadiumId}/star")
    @ResponseBody
    public ApiResponse starStadium(@PathVariable("stadiumId") Integer stadiumId, @Valid @RequestBody UserRequestDto userData, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult);
        }
        // star를 누르는 함수 -> star가 눌러지는 결과
        stadiumService.starStadium(userData.getUserId(), stadiumId);
        return ApiResponse.createSuccess("구장에 즐겨찾기가 되었습니다.");
    }
    
    @PostMapping("{stadiumId}/unstar")
    @ResponseBody
    public ApiResponse unstarStadium(@PathVariable("stadiumId") Integer stadiumId, @Valid @RequestBody UserRequestDto userData, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult);
        }
        // star를 제거하는 함수 -> star가 제거되는 결과
        stadiumService.unstarStadium(userData.getUserId(), stadiumId);
        return ApiResponse.createSuccess("구장 즐겨찾기가 해제되었습니다.");
    }
    
    @GetMapping("star")
    @ResponseBody
    public ApiResponse<List<Stadium>> getStarStadiumList(@RequestParam("userId") Integer userId){
        System.out.println(userId);
        return ApiResponse.createSuccess(stadiumService.getStarStadiumList(userId));
    }
    
    // 구장 목록 불러오기
    
    
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse handleIllegalArgumentException(IllegalArgumentException exception){
        return ApiResponse.createError(exception.getMessage());
    }
    
    @ExceptionHandler(ValidationException.class)
    public ApiResponse handleValidationException(ValidationException exception){
        return ApiResponse.createFail(exception.bindingResult);
    }
}
