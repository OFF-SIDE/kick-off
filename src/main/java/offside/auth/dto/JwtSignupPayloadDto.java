package offside.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtSignupPayloadDto {
    Integer id;
    String nickname;
    
    public JwtSignupPayloadDto(Integer id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}
