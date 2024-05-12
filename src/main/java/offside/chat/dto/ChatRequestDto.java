package offside.chat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import offside.chat.ChatRoomEnum;

@Getter
@Setter
@AllArgsConstructor
public class ChatRequestDto {
    @NotNull(message = "roomType을 입력해주세요. ex) REFEREE")
    private ChatRoomEnum roomType;
}
