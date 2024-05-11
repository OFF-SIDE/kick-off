package offside.auth.dto;

import lombok.Data;

@Data
public class KakaoUserInfoResponse {
    private Long id;
    private KakaoAccount kakao_account;
}