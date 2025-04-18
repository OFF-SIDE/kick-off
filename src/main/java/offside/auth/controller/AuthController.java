package offside.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import offside.auth.apiTypes.SocialLoginDto;
import offside.auth.apiTypes.SocialSignupDto;
import offside.auth.dto.UpdateUserInfoDto;
import offside.auth.service.AuthService;
import offside.response.ApiResponse;
import offside.response.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;
    
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    /**
     * 소셜 로그인
     * @param socialLoginDto
     * @param bindingResult
     * @return
     */
    @PostMapping("login/kakao")
    @ResponseBody
    public ApiResponse socialLogin(@RequestBody @Valid SocialLoginDto socialLoginDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new CustomException(bindingResult);
        }
        
        final var jwtTokenDto = authService.socialLogin(socialLoginDto);
        return ApiResponse.createSuccess(jwtTokenDto, "로그인이 완료되었습니다.");
    }
    
    /**
     * 소셜 회원가입
     * @param socialSignupDto
     * @param bindingResult
     * @return
     */
    @PostMapping("signup/kakao")
    @ResponseBody
    public ApiResponse socialSignup(@RequestBody @Valid SocialSignupDto socialSignupDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new CustomException(bindingResult);
        }
        
        final var jwtTokenDto = authService.socialSignup(socialSignupDto);
        return ApiResponse.createSuccess(jwtTokenDto, "회원가입이 완료되었습니다.");
    }
    
    /**
     * 내 정보 가져오기
     * @param request
     * @return
     */
    @GetMapping("")
    @ResponseBody
    public ApiResponse getUserDataFromJwt(HttpServletRequest request){
        final var token = this.authService.getTokenFromHeader(request);
        final var getUserData = authService.getAccountDataFromJwt(token);
        return ApiResponse.createSuccess(getUserData);
    }

    // 내 정보 수정하기
    @PostMapping("user/info/update")
    @ResponseBody
    public ApiResponse updateUserInfo(@RequestBody @Valid UpdateUserInfoDto updateUserInfoDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new CustomException(bindingResult);
        }
        authService.updateUserInfo(updateUserInfoDto);
        return ApiResponse.createSuccess("회원 정보 수정이 완료되었습니다.");
    }

    
    
}
