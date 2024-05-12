package offside.stadium.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import offside.auth.service.AuthService;
import offside.response.ApiResponse;
import offside.response.exception.CustomException;
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
    private final AuthService authService;
    
    @Autowired
    public StadiumController(StadiumService stadiumService,AuthService authService) {
        this.stadiumService = stadiumService;
        this.authService = authService;
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
            throw new CustomException(bindingResult);
        }
        return ApiResponse.createSuccess(stadiumService.getStadiumListByCategoryAndRange(searchParamData));
    }
    
    @GetMapping("location")
    @ResponseBody
    public ApiResponse<List<Stadium>> getStadiumListByCategoryAndLocation(@Valid LocationSearchParamDto searchParamData, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new CustomException(bindingResult);
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
    
    /**
     * 구장 평가하기
     * @param request
     * @param stadiumId
     * @param rateStadiumDto
     * @param bindingResult
     * @return StadiumRating
     */
    @PostMapping("{stadiumId}/rating")
    @ResponseBody
    public ApiResponse<StadiumRating> rateStadium(HttpServletRequest request,@PathVariable("stadiumId") Integer stadiumId,
        @Valid @RequestBody RateStadiumDto rateStadiumDto, BindingResult bindingResult) throws CustomException{
        if(bindingResult.hasErrors()){
            throw new CustomException(bindingResult);
        }
        final var token = this.authService.getTokenFromHeader(request);
        final var user = this.authService.getAccountDataFromJwt(token);
        
        // 0. 해당 유저가 로그인되어 있는지 확인
        return ApiResponse.createSuccess(stadiumService.rateStadium(stadiumId, user.getId(), rateStadiumDto));
    }
    
    /**
     * 구장 상세보기
     * @param request
     * @param stadiumId
     * @return StadiumWithInfoAndRatingAndStar
     */
    @GetMapping("{stadiumId}")
    @ResponseBody
    public ApiResponse<StadiumWithInfoAndRatingAndStar> getStadiumInformation(HttpServletRequest request,
        @PathVariable("stadiumId") Integer stadiumId) {
        final var token = this.authService.getTokenFromHeader(request);
        final var user = this.authService.getAccountDataFromJwt(token);
        
        return ApiResponse.createSuccess(stadiumService.getStadiumInformation(stadiumId, user.getId()));
    }

    @PostMapping("{stadiumId}/star")
    @ResponseBody
    public ApiResponse starStadium(HttpServletRequest request, @PathVariable("stadiumId") Integer stadiumId,
        BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new CustomException(bindingResult);
        }
        final var token = this.authService.getTokenFromHeader(request);
        final var user = this.authService.getAccountDataFromJwt(token);
        
        stadiumService.starStadium(user.getId(), stadiumId);
        return ApiResponse.createSuccess("구장에 즐겨찾기가 되었습니다.");
    }
    
    /**
     * 구장 즐겨찾기 해제
     * @param request
     * @param stadiumId
     * @param bindingResult
     * @return
     */
    @PostMapping("{stadiumId}/unstar")
    @ResponseBody
    public ApiResponse unstarStadium(HttpServletRequest request, @PathVariable("stadiumId") Integer stadiumId,
        BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new CustomException(bindingResult);
        }
        final var token = this.authService.getTokenFromHeader(request);
        final var user = this.authService.getAccountDataFromJwt(token);
        
        // star를 제거하는 함수 -> star가 제거되는 결과
        stadiumService.unstarStadium(user.getId(), stadiumId);
        return ApiResponse.createSuccess("구장 즐겨찾기가 해제되었습니다.");
    }
    
    /**
     * 즐겨찾기한 구장 목록 가져오기
     * @param request
     * @return
     */
    @GetMapping("star")
    @ResponseBody
    public ApiResponse<List<Stadium>> getStarStadiumList(HttpServletRequest request){
        final var token = this.authService.getTokenFromHeader(request);
        final var user = this.authService.getAccountDataFromJwt(token);
        
        return ApiResponse.createSuccess(stadiumService.getStarStadiumList(user.getId()));
    }
    
    // 구장 목록 불러오기
    
}
