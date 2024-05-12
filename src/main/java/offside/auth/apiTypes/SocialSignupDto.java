package offside.auth.apiTypes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import offside.CategoryEnum;
import offside.LocationEnum;
import offside.auth.SocialProviderEnum;

@Getter
@Setter
@AllArgsConstructor
public class SocialSignupDto {
    @NotBlank(message = "oauthId를 입력해주세요")
    private String oauthId;
    @NotBlank(message = "name을 입력해주세요")
    private String name;
    @NotBlank(message = "nickname을 입력해주세요")
    private String nickname;
    
    @NotNull(message = "location을 입력해주세요")
    private LocationEnum location;
    @NotNull(message = "category를 입력해주세요")
    private CategoryEnum category;
    private SocialProviderEnum socialProvider = SocialProviderEnum.KAKAO;
    
    public SocialSignupDto() {
    }
}
