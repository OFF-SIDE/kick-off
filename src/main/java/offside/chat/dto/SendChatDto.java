package offside.chat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import offside.chat.ChatRoomEnum;

@Getter
@Setter
@AllArgsConstructor
public class SendChatDto {
    @NotNull(message = "roomId를 입력해주세요. ex) 심판글 번호")
    private Integer roomId;
    
    @NotNull(message = "roomType을 입력해주세요. ex) REFEREE")
    private ChatRoomEnum roomType;
    
    @NotNull(message = "message를 입력해주세요.")
    private String message;
    
    public SendChatDto() {}
}
