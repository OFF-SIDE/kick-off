package offside.auth.apiTypes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SocialLoginDto {
    @NotBlank(message = "oauthId를 주세요")
    private String oauthId;
    
    public SocialLoginDto() {}
}
