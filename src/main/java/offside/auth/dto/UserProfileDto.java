package offside.auth.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import offside.auth.domain.Account;

@Getter
@Setter
@AllArgsConstructor
public class UserProfileDto {
    private Integer id;
    private String nickname;
    private String profileImage;
    private LocalDateTime createdAt;
    
    public UserProfileDto(Account account){
        this.id = account.getId();
        this.nickname = account.getNickname();
        this.profileImage = account.getProfileImage();
        this.createdAt = account.getCreatedAt();
    }
}
