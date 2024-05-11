package offside.auth.service;

import offside.auth.apiTypes.UserSocialLoginDto;
import offside.auth.dto.JwtAccountPayloadDto;
import offside.auth.dto.JwtSignupPayloadDto;
import offside.auth.dto.KakaoUserInfoResponse;
import offside.auth.dto.SocialLoginResponseDto;
import offside.auth.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AuthService {
    
    private final AccountRepository accountRepository;
    private final JwtGenerator jwtGenerator;
    private final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";
    
    @Autowired
    public AuthService(AccountRepository accountRepository, JwtGenerator jwtGenerator) {
        this.accountRepository = accountRepository;
        this.jwtGenerator = jwtGenerator;
    }
    
    // 카카오에서 사용자 정보 가져오기 by 토큰
    public KakaoUserInfoResponse getKakaoDataByToken(String oauthToken){
        WebClient webClient = WebClient.builder().baseUrl(USER_INFO_URI).build();
    
        final var webClientResponse = webClient.get()
            .header("Authorization", "Bearer " + oauthToken)
            .retrieve()
            .bodyToFlux(KakaoUserInfoResponse.class);
        return webClientResponse.blockFirst();
    }
    
    public SocialLoginResponseDto socialLogin(UserSocialLoginDto userSocialLoginDto){
        final var kakaoUserInfo = getKakaoDataByToken(userSocialLoginDto.oauthToken);
        
        final var account = this.accountRepository.findByOauthId(kakaoUserInfo.getId().toString());
        if(account.isEmpty()){
            final var jwtToken= jwtGenerator.generateSignupToken(new JwtSignupPayloadDto(kakaoUserInfo.getId().intValue(), kakaoUserInfo.getKakao_account().getName()));
            return new SocialLoginResponseDto(jwtToken,false);
        }
        final var jwtToken= jwtGenerator.generateLoginToken(new JwtAccountPayloadDto(account.get().id, account.get().nickname, account.get().location, account.get().category));
        return new SocialLoginResponseDto(jwtToken,true);
    }
    
    
}
