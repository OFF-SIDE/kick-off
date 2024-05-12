package offside.chat.controller;

import offside.chat.ChatRoom;
import offside.chat.service.ChatService;
import offside.chat.CreateChatRoomDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("chat")
public class ChatController {
    private final ChatService chatService;
    
    @Autowired
    public ChatController(ChatService chatService){
        this.chatService = chatService;
        System.out.println("test");
    }
    
    @PostMapping()
    @ResponseBody
    public ChatRoom createChatRoom(@RequestBody CreateChatRoomDto createChatRoomDto) {
        return chatService.createChatRoom(createChatRoomDto.getName());
    }
//    @GetMapping("/rooms")
//    @ResponseBody
//    public List<ChatRoom> getAllChatRooms(@RequestBody String userId){}
    

}
