package offside.auth.controller;

import offside.auth.apiTypes.UserSocialLoginDto;
import offside.auth.service.AuthService;
import offside.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PostMapping("login")
    @ResponseBody
    public ApiResponse socialLogin(@RequestBody UserSocialLoginDto userSocialLoginDto){
        System.out.println(userSocialLoginDto.oauthToken);
        final var socialLoginResponse = authService.socialLogin(userSocialLoginDto);
        if(socialLoginResponse.isLogin){
            return ApiResponse.createSuccess(socialLoginResponse.jwtTokens.getAccessToken(),"로그인에 성공했습니다.");
        }else{
            return ApiResponse.createError(socialLoginResponse.jwtTokens.getAccessToken(),"로그인에 실패했습니다. 회원가입을 먼저 진행해주세요");
        }
    }
}
