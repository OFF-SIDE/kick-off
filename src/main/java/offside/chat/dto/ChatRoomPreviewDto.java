package offside.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import offside.auth.domain.Account;
import offside.auth.dto.UserProfileDto;
import offside.chat.entity.Chatting;

@Getter
@Setter
@AllArgsConstructor
public class ChatRoomPreviewDto {
    private Chatting chatting;
    private UserProfileDto userProfile;
}
