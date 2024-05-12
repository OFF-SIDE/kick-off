package offside.chat.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import offside.auth.dto.UserProfileDto;
import offside.auth.service.AuthService;
import offside.chat.ChatRoomEnum;
import offside.chat.dto.ChatRequestDto;
import offside.chat.dto.ChatRoomDto;
import offside.chat.dto.ChatRoomPreviewDto;
import offside.chat.entity.Chatting;
import offside.chat.service.ChatService;
import offside.chat.dto.SendChatDto;
import offside.response.ApiResponse;
import offside.response.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("chat")
public class ChatController {
    private final ChatService chatService;
    private final AuthService authService;
    
    @Autowired
    public ChatController(ChatService chatService, AuthService authService){
        this.chatService = chatService;
        this.authService = authService;
    }
    
    /**
     * 채팅 전송 API
     * @param request
     * @param sendChatDto
     * @param bindingResult
     * @return Chatting
     */
    @PostMapping()
    @ResponseBody
    public ApiResponse<Chatting> sendChat(HttpServletRequest request,@RequestBody SendChatDto sendChatDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new CustomException(bindingResult);
        }
        final var user = this.authService.getAccountDataFromJwt(this.authService.getTokenFromHeader(request));
        final var chat = this.chatService.sendChat(user.getId(), sendChatDto);
        return ApiResponse.createSuccess(chat, "채팅이 전송되었습니다.");
    }
    
    /**
     * 특정 채팅방에 입장했을 때 채팅방 데이터(채팅방, 상대프로필, 채팅 내역) 가져오기 API.
     * @param request
     * @param roomId
     * @param roomType
     * @return
     */
    @GetMapping("{roomId}")
    @ResponseBody
    public ApiResponse<ChatRoomDto> getChatRoomData(HttpServletRequest request,@PathVariable("roomId") Integer roomId,
        @RequestParam("roomType") ChatRoomEnum roomType){
        final var user = this.authService.getAccountDataFromJwt(this.authService.getTokenFromHeader(request));
        roomType = roomType != null ? roomType : ChatRoomEnum.REFEREE;
        
        final var chattingRoomData = this.chatService.getChattingRoomData(user.getId(), roomId, roomType);
        return ApiResponse.createSuccess(chattingRoomData);
    }
    
    /**
     * 특정 채팅방의 채팅 내용 가져오기 API
     * @param request
     * @param roomId
     * @param roomType
     * @return
     */
    @GetMapping("{roomId}/chat")
    @ResponseBody
    public ApiResponse<List<Chatting>> getChattingInARoom(HttpServletRequest request,@PathVariable("roomId") Integer roomId,
        @RequestParam("roomType") ChatRoomEnum roomType,@RequestParam("lastChatId") Integer lastChatId){
        final var user = this.authService.getAccountDataFromJwt(this.authService.getTokenFromHeader(request));
        roomType = roomType != null ? roomType : ChatRoomEnum.REFEREE;
        lastChatId = lastChatId > 0 ? lastChatId : 0;
        
        final var chattingList = this.chatService.getChattingListInARoom(user.getId(), roomId, roomType, lastChatId);
        return ApiResponse.createSuccess(chattingList);
    }
    
    /**
     * 채팅방 목록 가져오기 API
     * @param request
     * @return
     */
    @GetMapping("room")
    @ResponseBody
    public ApiResponse<List<ChatRoomPreviewDto>> getAllMyChatRooms(HttpServletRequest request){
        final var user = this.authService.getAccountDataFromJwt(this.authService.getTokenFromHeader(request));
        final var chatRoomList = this.chatService.getAllMyChatRooms(user.getId());
        return ApiResponse.createSuccess(chatRoomList);
    }
    
}
