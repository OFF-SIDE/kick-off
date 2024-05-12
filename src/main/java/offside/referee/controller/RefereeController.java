package offside.referee.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.List;

import offside.auth.service.AuthService;
import offside.referee.apiTypes.*;
import offside.referee.domain.Referee;
import offside.referee.dto.RefereeSummaryDto;
import offside.referee.service.RefereeService;
import offside.response.ApiResponse;
import offside.response.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("referee")
public class RefereeController {
    private final RefereeService refereeService;
    private final AuthService authService;
    
    @Autowired
    private RefereeController(RefereeService refereeService,AuthService authService){
        this.refereeService = refereeService;
        this.authService =authService;
    }

    // 1. 구인글 작성
    @PostMapping("hiring")
    @ResponseBody
    public ApiResponse<Referee> refereeHiring(HttpServletRequest request,
        @Valid @RequestBody CreateRefereeHiringDto createRefereeHiringDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){ // 유효성 검사
            throw new CustomException(bindingResult);
        }
        final var token = this.authService.getTokenFromHeader(request);
        final var user = this.authService.getAccountDataFromJwt(token);
        return ApiResponse.createSuccess(refereeService.refereeHiring(user.getId(),createRefereeHiringDto));
    }
    
    
    // 2. 지원글 작성
    @PostMapping("jiwon")
    @ResponseBody
    public ApiResponse<Referee> refereeJiwon(HttpServletRequest request,
        @RequestBody @Valid CreateRefereeJiwonDto createRefereeJiwonDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){ // 유효성 검사
            throw new CustomException(bindingResult);
        }
        final var token = this.authService.getTokenFromHeader(request);
        final var user = this.authService.getAccountDataFromJwt(token);
        return ApiResponse.createSuccess(refereeService.refereeJiwon(user.getId(),createRefereeJiwonDto));
    }

//     3. 구인/지원 글 목록 불러오기 (날짜순) + Q. 지역, 날짜, 시간, 예약/마감(상태)
    @GetMapping()
    @ResponseBody
    public ApiResponse<List<RefereeSummaryDto>> getRefereeList(@Valid RefereeSearchDto refereeSearchDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){ // 유효성 검사
            throw new CustomException(bindingResult);
        }
        return ApiResponse.createSuccess(refereeService.getRefereeListBySearchDto(refereeSearchDto));
    }
    
//     4. 글 상태 변경하기 (사용자가 본인 글 마감처리)
    @PatchMapping("{refereeId}/status")
    @ResponseBody
    public ApiResponse<Referee> changeStatus(HttpServletRequest request,
        @PathVariable("refereeId") Integer refereeId, @RequestBody @Valid ChangeStatusDto newStatus, BindingResult bindingResult){
        if(bindingResult.hasErrors()){ // 유효성 검사
            throw new CustomException(bindingResult);
        }
        final var token = this.authService.getTokenFromHeader(request);
        final var user = this.authService.getAccountDataFromJwt(token);
        return ApiResponse.createSuccess(refereeService.changeRefereeStatus(refereeId,user.getId(), newStatus));
    }

    // 5. 구인/지원 글 인기순 or 최신순으로 각 2/3개씩 불러오기
    @GetMapping("summary")
    @ResponseBody
    public ApiResponse<List<Referee>> miriboki(@RequestParam("isHiring") Boolean isHiring){
        return ApiResponse.createSuccess(refereeService.miriboki(isHiring));
    }
    
    
    /**
     * 심판글 스크랩하기
     * @param refereeId
     * @param request
     * @return
     */
    @PostMapping("{refereeId}/star")
    @ResponseBody
    public ApiResponse refereeStar(@PathVariable("refereeId") Integer refereeId, HttpServletRequest request ){
        final var token = this.authService.getTokenFromHeader(request);
        final var user = this.authService.getAccountDataFromJwt(token);
        refereeService.refereeStar(user.getId(), refereeId);
        return ApiResponse.createSuccess("심판 스크랩 등록되었습니다.");
    }
    
    /**
     * 심판글 스크랩 제거
     * @param refereeId
     * @param request
     * @return
     */
    @DeleteMapping("{refereeId}/star")
    @ResponseBody
    public ApiResponse deleteRefereeStar(@PathVariable("refereeId") Integer refereeId, HttpServletRequest request){
        final var token = this.authService.getTokenFromHeader(request);
        final var user = this.authService.getAccountDataFromJwt(token);
        refereeService.deleteRefereeStar(user.getId(), refereeId);
        return ApiResponse.createSuccess("심판 스크랩 해제되었습니다.");
    }

    @GetMapping("star")
    @ResponseBody
    public ApiResponse<List<Referee>> getStarRefereeList(HttpServletRequest request,@RequestParam("isHiring") Boolean isHiring){
        isHiring = isHiring ? true : false;
        final var token = this.authService.getTokenFromHeader(request);
        final var user = this.authService.getAccountDataFromJwt(token);
        return ApiResponse.createSuccess(refereeService.getStarRefereeList(user.getId(), isHiring));
    }
}
