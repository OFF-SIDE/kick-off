package offside.referee.controller;

import jakarta.validation.Valid;

import java.util.List;

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
    
    @Autowired
    private RefereeController(RefereeService refereeService){
        this.refereeService = refereeService;
    }

    // 1. 구인글 작성
    @PostMapping("hiring")
    @ResponseBody
    public ApiResponse<Referee> refereeHiring(@Valid @RequestBody CreateRefereeHiringDto createRefereeHiringDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){ // 유효성 검사
            throw new CustomException(bindingResult);
        }
        return ApiResponse.createSuccess(refereeService.refereeHiring(createRefereeHiringDto));
    }
    
    
    // 2. 지원글 작성
    @PostMapping("jiwon")
    @ResponseBody
    public ApiResponse<Referee> refereeJiwon(@RequestBody @Valid CreateRefereeJiwonDto createRefereeJiwonDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){ // 유효성 검사
            throw new CustomException(bindingResult);
        }
        return ApiResponse.createSuccess(refereeService.refereeJiwon(createRefereeJiwonDto));
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
    public ApiResponse<Referee> changeStatus(@PathVariable("refereeId") Integer refereeId,@RequestBody @Valid ChangeStatusDto newStatus, BindingResult bindingResult){
        if(bindingResult.hasErrors()){ // 유효성 검사
            throw new CustomException(bindingResult);
        }
        return ApiResponse.createSuccess(refereeService.changeRefereeStatus(refereeId, newStatus));
    }

    // 5. 구인/지원 글 인기순 or 최신순으로 각 2/3개씩 불러오기
    

    // 6. 심판 rating
    
    
    // 7. 구인/지원 글 스크랩
    @PostMapping("{refereeId}/star")
    @ResponseBody
    public ApiResponse refereeStar(@PathVariable("refereeId") Integer refereeId, @RequestBody @Valid RefereeUserRequestDto refereeUserRequestDto, BindingResult bindingResult ){
        if(bindingResult.hasErrors()){ // 유효성 검사
            throw new CustomException(bindingResult);
        }
        refereeService.refereeStar(refereeUserRequestDto.getUserId(), refereeId);
        return ApiResponse.createSuccess("심판 스크랩 등록되었습니다.");
    }

    @PostMapping("{refereeId}/unstar")
    @ResponseBody
    public ApiResponse refereeUnstar(@PathVariable("refereeId") Integer refereeId, @RequestBody @Valid RefereeUserRequestDto refereeUserRequestDto, BindingResult bindingResult ){
        if(bindingResult.hasErrors()){ // 유효성 검사
            throw new CustomException(bindingResult);
        }
        refereeService.refereeUnstar(refereeUserRequestDto.getUserId(), refereeId);
        return ApiResponse.createSuccess("심판 스크랩 해제되었습니다.");
    }

    @GetMapping("star")
    @ResponseBody
    public ApiResponse<List<Referee>> getStarRefereeList(@RequestBody @Valid RefereeUserRequestIsHiringDto refereeUserRequestIsHiringDto){
        return ApiResponse.createSuccess(refereeService.getStarRefereeList(refereeUserRequestIsHiringDto.getUserId(), refereeUserRequestIsHiringDto.getIsHiring()));
    }
}
