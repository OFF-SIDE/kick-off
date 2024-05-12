package offside.chat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import offside.chat.ChatRoomEnum;
import offside.chat.dto.SendChatDto;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
public class Chatting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    
    public Integer senderId;
    public Integer receiverId;
    public Integer roomId;
    public ChatRoomEnum roomType;
    public String message;
    public LocalDateTime sendAt;
    
    @ColumnDefault("false")
    public boolean isRead;
    
    public Chatting() {}
    
    public Chatting(Integer senderId, Integer receiverId, Integer roomId, ChatRoomEnum roomType,
        String message) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.roomId = roomId;
        this.roomType = roomType;
        this.message = message;
        this.sendAt =  LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
    
    public Chatting(Integer senderId, Integer receiverId, SendChatDto sendChatDto) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.roomId = sendChatDto.getRoomId();
        this.roomType = sendChatDto.getRoomType();
        this.message = sendChatDto.getMessage();
        this.sendAt =  LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
