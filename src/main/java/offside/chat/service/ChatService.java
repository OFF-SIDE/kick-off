package offside.chat.service;

import offside.chat.ChatRoom;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final Map<String, ChatRoom> chatRoomList;
    @Autowired
    public ChatService(){
        chatRoomList = new HashMap<>();
    }
    
    public ChatRoom createChatRoom(String name){
        ChatRoom chatRoom = new ChatRoom(name);
        chatRoomList.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }
    
}
