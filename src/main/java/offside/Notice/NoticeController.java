package offside.Notice;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import offside.response.ApiResponse;
import offside.response.exception.CustomException;
import offside.response.exception.CustomExceptionTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("notice")
public class NoticeController {
    private final NoticeRepository noticeRepository;
    
    @Autowired
    private NoticeController(NoticeRepository noticeRepository){
        this.noticeRepository = noticeRepository;
    }

    //공지사항 목록보기
    @GetMapping()
    @ResponseBody
    public ApiResponse<List<Notice>> getNoticeList(){
        return ApiResponse.createSuccess(noticeRepository.findAllByOrderByCreatedAtDesc());
    }
    
    //공지사항 상세보기
    @GetMapping("{noticeId}")
    @ResponseBody
    public ApiResponse<Notice> getNotice(@PathVariable Integer noticeId) {
        Optional<Notice> notice = noticeRepository.findById(noticeId);
        if(notice.isEmpty()){
            throw new CustomException(CustomExceptionTypes.NOTICE_NOT_FOUND);
        }
        return ApiResponse.createSuccess(notice.get());
    }
}
