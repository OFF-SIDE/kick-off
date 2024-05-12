package offside.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import offside.auth.apiTypes.SocialLoginDto;
import offside.auth.apiTypes.SocialSignupDto;
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
    
    // 로그인
    @PostMapping("login/kakao")
    @ResponseBody
    public ApiResponse socialLogin(@RequestBody @Valid SocialLoginDto socialLoginDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new CustomException(bindingResult);
        }
        
        final var jwtTokenDto = authService.socialLogin(socialLoginDto);
        return ApiResponse.createSuccess(jwtTokenDto, "로그인이 완료되었습니다.");
    }
    
    @PostMapping("signup/kakao")
    @ResponseBody
    public ApiResponse socialSignup(@RequestBody @Valid SocialSignupDto socialSignupDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new CustomException(bindingResult);
        }
        
        final var jwtTokenDto = authService.socialSignup(socialSignupDto);
        return ApiResponse.createSuccess(jwtTokenDto, "회원가입이 완료되었습니다.");
    }
    
    @GetMapping("")
    @ResponseBody
    public ApiResponse getUserDataFromJwt(HttpServletRequest request){
        final var token = this.authService.getTokenFromHeader(request);
        final var getUserData = authService.getAccountDataFromJwt(token);
        return ApiResponse.createSuccess(getUserData);
    }
    
    
}
