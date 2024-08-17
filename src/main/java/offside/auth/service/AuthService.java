package offside.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import offside.CategoryEnum;
import offside.LocationEnum;
import offside.auth.SocialProviderEnum;
import offside.auth.apiTypes.SocialLoginDto;
import offside.auth.apiTypes.SocialSignupDto;
import offside.auth.domain.Account;
import offside.auth.dto.JwtAccountPayloadDto;
import offside.auth.dto.JwtTokenDto;
import offside.auth.dto.KakaoUserInfoResponse;
import offside.auth.repository.AccountRepository;
import offside.response.exception.CustomException;
import offside.response.exception.CustomExceptionTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AuthService {
    
    private final AccountRepository accountRepository;
    private final JwtService jwtService;
    private final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    
    @Autowired
    public AuthService(AccountRepository accountRepository, JwtService jwtService) {
        this.accountRepository = accountRepository;
        this.jwtService = jwtService;
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
    
    /**
     * 소셜로그인을 진행하는 함수. 가입 정보 없을 시 에러
     * @param socialLoginDto
     * @return JwtTokenDto
     * @throw USER_NOT_FOUND;
     */
    public JwtTokenDto socialLogin(SocialLoginDto socialLoginDto){
        final var account = this.accountRepository.findByOauthIdAndSocialProvider(socialLoginDto.getOauthId(), SocialProviderEnum.KAKAO);
        if(account.isEmpty()){
            throw new CustomException(CustomExceptionTypes.USER_NOT_FOUND);
        }
        return jwtService.createLoginToken(new JwtAccountPayloadDto(account.get().id, account.get().getName(),account.get().nickname, account.get().location, account.get().category, account.get().profileImage));
    }
    
    /**
     * 소셜 회원가입을 진행하는 함수. 이미 가입 유저 시 에러.
     * @param socialSignupDto
     * @return JwtTokenDto
     * @throw USER_ALREADY_EXIST
     */
    public JwtTokenDto socialSignup(SocialSignupDto socialSignupDto){
        final var previousAccount = this.accountRepository.findByOauthIdAndSocialProvider(socialSignupDto.getOauthId(), SocialProviderEnum.KAKAO);
        if(previousAccount.isPresent()){
            throw new CustomException(CustomExceptionTypes.USER_ALREADY_EXIST);
        }

        final var account = accountRepository.save(new Account(socialSignupDto));
        return jwtService.createLoginToken(new JwtAccountPayloadDto(account.getId(),account.getName(), account.getNickname(), account.getLocation(), account.getCategory(), account.getProfileImage()));
    }
    
    /**
     * @summary JWT 토큰에서 계정 정보 추출
     * @param accessToken
     * @return JwtAccountPayloadDto
     * @throw TOKEN_UNAUTHORIZED_ERROR
     */
    public JwtAccountPayloadDto getAccountDataFromJwt(String accessToken){
        final var claims = this.jwtService.validateToken(accessToken);

        final var id = claims.get("id",Integer.class);
        final var name= claims.get("name",String.class);
        final var nickname = claims.get("nickname", String.class);
        final var location = claims.get("location", String.class);
        final var category = claims.get("category", String.class);
        if(id == null || name == null || nickname == null || location == null || category == null){
            throw new CustomException(CustomExceptionTypes.TOKEN_UNAUTHORIZED_ERROR);
        }
        return new JwtAccountPayloadDto(id,name,nickname, LocationEnum.valueOf(location),
            CategoryEnum.valueOf(category), profileImage);
    }
    
    public String getTokenFromHeader(HttpServletRequest request) throws CustomException{
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        throw new CustomException(CustomExceptionTypes.TOKEN_NOT_FOUND);
    }
}
