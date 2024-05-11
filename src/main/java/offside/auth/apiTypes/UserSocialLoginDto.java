package offside.auth.apiTypes;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSocialLoginDto {
    @NotNull(message = "oauthToken을 주세요")
    public String oauthToken;
    
    public UserSocialLoginDto() {}
    
    public UserSocialLoginDto(String oauthToken) {
        this.oauthToken = oauthToken;
    }
}
