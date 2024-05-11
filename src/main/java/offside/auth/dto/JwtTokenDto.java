package offside.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtTokenDto {
    String accessToken;
    
    public JwtTokenDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
