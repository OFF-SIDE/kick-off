package offside.auth.apiTypes;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSocialLoginDto {
    @NotNull(message = "kakaoId를 주세요")
    public String userId;
    
    public UserSocialLoginDto() {}
    
    public UserSocialLoginDto(String userId) {
        this.userId = userId;
    }
}
