package offside.chat.repository;

import jakarta.transaction.Transactional;
import java.util.List;
import offside.chat.ChatRoomEnum;
import offside.chat.entity.Chatting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ChattingRepository extends JpaRepository<Chatting, Long> {
    
    @Query("SELECT ch FROM Chatting ch WHERE ch.id > :lastChatId and ((ch.senderId =: senderId and ch.receiverId = :receiverId) or (ch.senderId =: receiverId and ch.receiverId = :senderId)) and ch.roomId = :roomId and ch.roomType = :roomType")
    List<Chatting> findAllChattingInARoomOrderByDesc(@Param("lastChatId") Integer lastChatId,@Param("senderId") Integer senderId,@Param("receiverId") Integer receiverId,
        @Param("roomId") Integer roomId, @Param("roomType") ChatRoomEnum roomType);
    
    @Query("SELECT ch FROM Chatting ch WHERE ch.id IN (" +
        "SELECT MAX(ch2.id) FROM Chatting ch2 WHERE ch2.roomId = ch.roomId AND " +
           "(ch2.senderId = :userId OR ch2.receiverId = :userId) GROUP BY ch2.roomId)")
    List<Chatting> findAllChatRoomListByUserId(@Param("userId") Integer userId);
    // id, roomId, senderId, receiverId, roomType, message, sentAt
    
}
