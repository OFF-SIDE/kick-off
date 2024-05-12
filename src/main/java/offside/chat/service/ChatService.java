package offside.chat.service;

import java.util.ArrayList;
import java.util.List;
import offside.auth.dto.UserProfileDto;
import offside.auth.repository.AccountRepository;
import offside.chat.ChatRoomEnum;
import offside.chat.dto.ChatRoomDto;
import offside.chat.dto.ChatRoomPreviewDto;
import offside.chat.dto.SendChatDto;
import offside.chat.entity.Chatting;
import offside.chat.repository.ChattingRepository;
import offside.common.dto.Article;
import offside.referee.dto.RefereeArticleDto;
import offside.referee.repository.RefereeRepository;
import offside.response.exception.CustomException;
import offside.response.exception.CustomExceptionTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChattingRepository chattingRepository;
    private final RefereeRepository refereeRepository;
    private final AccountRepository accountRepository;
    
    @Autowired
    public ChatService(ChattingRepository chattingRepository, RefereeRepository refereeRepository,AccountRepository accountRepository){
        this.chattingRepository = chattingRepository;
        this.refereeRepository = refereeRepository;
        this.accountRepository = accountRepository;
    }
    
    /**
     * 해당 room 에 채팅 보내기
     * @param userId
     * @param sendChatDto
     * @return Chatting
     * @throw CHATROOM_NOT_FOUND, CHAT_MYSELF_ERROR
     */
    public Chatting sendChat(Integer userId, SendChatDto sendChatDto){
        final var receiverId = this.findReceiverIdByRoomId(sendChatDto.getRoomId(),sendChatDto.getRoomType());
        if(userId == receiverId){
            throw new CustomException(CustomExceptionTypes.CHAT_MYSELF_ERROR);
        }
        return this.chattingRepository.save(new Chatting(userId,receiverId, sendChatDto));
    }
    
    /**
     * roomId로 receiverId 받아오기
     * @param roomId
     * @param chatRoomEnum
     * @return receiverId
     * @throw CHATROOM_NOT_FOUND
     */
    public Integer findReceiverIdByRoomId(Integer roomId, ChatRoomEnum chatRoomEnum){
        if(chatRoomEnum == ChatRoomEnum.REFEREE){
            final var referee = this.refereeRepository.findById(roomId);
            if(referee.isEmpty()){
                throw new CustomException(CustomExceptionTypes.CHATROOM_NOT_FOUND);
            }
            return referee.get().getUserId();
        }
        throw new CustomException(CustomExceptionTypes.CHATROOM_NOT_FOUND);
    }
    
    /**
     * userId, roomId로 해당 채팅방 내용 긁어오기
     * @param userId
     * @param roomId
     * @param roomType
     * @param lastChatId
     * @return
     */
    public List<Chatting> getChattingListInARoom(Integer userId, Integer roomId, ChatRoomEnum roomType, Integer lastChatId){
        final var receiverId = this.findReceiverIdByRoomId(roomId, roomType);
        final var chattingList = this.chattingRepository.findAllChattingInARoomOrderByDesc(lastChatId,userId,receiverId,roomId,roomType);
        return chattingList;
    }
    
    /**
     * 채팅방 목록과 채팅 상대 정보 가져오기
     * @param userId
     * @return
     */
    public List<ChatRoomPreviewDto> getAllMyChatRooms(Integer userId){
        final var chatRoomList = this.chattingRepository.findAllChatRoomListByUserId(userId);
        List<ChatRoomPreviewDto> chatRoomPreviewDtoList = new ArrayList<>();
        chatRoomList.stream().forEach(chatRoom ->{
            Integer oppId = chatRoom.getReceiverId();
            if(oppId == userId) oppId = chatRoom.getSenderId();
            
            final var account = this.accountRepository.findById(oppId);
            if(account.isPresent())
                chatRoomPreviewDtoList.add(new ChatRoomPreviewDto(chatRoom,new UserProfileDto(account.get())));
        });
        
        return chatRoomPreviewDtoList;
    }
    
    public ChatRoomDto getChattingRoomData(Integer userId, Integer roomId, ChatRoomEnum roomType){
        // 1. 채팅 목록
        final var chattingList = this.getChattingListInARoom(userId,roomId, roomType,0);
        
        // 2. Article 가져오기
        Article article = this.getArticle(roomId,roomType);
        
        // 3. 상대방의 UserId로 UserProfile 가져오기
        /// 1. 첫 채팅방 생성 -> userId := article.writerId
        /// 2. 이미 있는 채팅방에서 글 작성 주인에서 글 쓰기 -> article.writerId
        /// 3. 이미 있는 채팅방에서 글 주인이 나한테 걸 때 -> chatting에서 sender, receiver중에 나 아닌거
        Integer oppId = article.getWriterId();
        
        if(chattingList.size() > 0 && userId == oppId){
            oppId = chattingList.get(0).getReceiverId();
            if(oppId == userId) oppId = chattingList.get(0).getSenderId();
        }
        final var opp = accountRepository.findById(oppId);
        if(opp.isEmpty()) throw new CustomException(CustomExceptionTypes.USER_NOT_FOUND);
        final var oppProfile = new UserProfileDto(opp.get());
    
        return new ChatRoomDto(chattingList, oppProfile, article);
    }
    
    /**
     * roomId와 roomType으로 글 가져오기
     * @param articleId
     * @param articleType
     * @return
     */
    public Article getArticle(Integer articleId,ChatRoomEnum articleType){
        if(articleType == ChatRoomEnum.REFEREE){
            final var referee = this.refereeRepository.findById(articleId);
            if(referee.isEmpty())
                throw new CustomException(CustomExceptionTypes.USER_NOT_FOUND);
            return new RefereeArticleDto(referee.get());
        }
        throw new CustomException(CustomExceptionTypes.ARTICLE_NOT_FOUND);
    }
    
}
