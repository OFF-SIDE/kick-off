package offside.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialLoginResponseDto {
    public JwtTokenDto jwtTokens;
    public boolean isLogin;
    
    public SocialLoginResponseDto(JwtTokenDto jwtTokens, boolean isLogin) {
        this.jwtTokens = jwtTokens;
        this.isLogin = isLogin;
    }
    
    public SocialLoginResponseDto(){}
}
