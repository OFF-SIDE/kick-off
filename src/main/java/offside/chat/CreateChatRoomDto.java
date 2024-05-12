package offside.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateChatRoomDto {
    private String name;
    
    public CreateChatRoomDto() {}
}
