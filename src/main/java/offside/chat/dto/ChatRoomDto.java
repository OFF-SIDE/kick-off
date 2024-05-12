package offside.chat.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import offside.auth.dto.UserProfileDto;
import offside.chat.entity.Chatting;
import offside.common.dto.Article;

@Getter
@Setter
@AllArgsConstructor
public class ChatRoomDto {
    private List<Chatting> chattingList;
    private UserProfileDto userProfile;
    private Article article;
}
