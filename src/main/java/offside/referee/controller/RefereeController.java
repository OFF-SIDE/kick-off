package offside.referee.controller;

import jakarta.validation.Valid;
import java.sql.Ref;
import offside.referee.apiTypes.CreateRefereeHiringDto;
import offside.referee.apiTypes.CreateRefereeJiwonDto;
import offside.referee.domain.Referee;
import offside.referee.service.RefereeService;
import offside.response.ApiResponse;
import offside.response.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
            throw new ValidationException(bindingResult);
        }
        return ApiResponse.createSuccess(refereeService.refereeHiring(createRefereeHiringDto));
    }
    
    
    // 2. 지원글 작성
    @PostMapping("Jiwon")
    @ResponseBody
    public ApiResponse<Referee> refereeJiwon(@RequestBody @Valid CreateRefereeJiwonDto createRefereeJiwonDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){ // 유효성 검사
            throw new ValidationException(bindingResult);
        }
        return ApiResponse.createSuccess(refereeService.refereeJiwon(createRefereeJiwonDto));
    }
    
    
    // 3. 구인/지원 글 목록 불러오기 (날짜순) + Q. 지역, 날짜, 시간, 예약/마감(상태)
    
    
    // 4. 글 상태 변경하기 (사용자가 본인 글 마감처리)


    // 5. 구인/지원 글 인기순 or 최신순으로 각 2/3개씩 불러오기
    

    // 6. 심판 rating
    
    
    // 7. 구인/지원 글 스크랩
    
    
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse handleIllegalArgumentException(IllegalArgumentException exception){
        return ApiResponse.createError(exception.getMessage());
    }
    
    @ExceptionHandler(ValidationException.class)
    public ApiResponse handleValidationException(ValidationException exception){
        return ApiResponse.createFail(exception.bindingResult);
    }
}
