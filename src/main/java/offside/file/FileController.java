package offside.file;

import jakarta.validation.Valid;
import offside.file.apiTypes.CreateFileUploadUrlDto;
import offside.file.dto.PresignedUrlDto;
import offside.response.ApiResponse;
import offside.response.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("file")
public class FileController {
    private final FileService fileService;
    @Autowired
    private FileController(FileService fileService){
        this.fileService = fileService;
    }
    
    /**
     * @summary 파일 업로드를 위한 S3 업로드 URL 생성
     * @return ApiResponse<?>
     */
    @PostMapping("upload")
    public ApiResponse<PresignedUrlDto> getUploadUrl(@RequestBody @Valid CreateFileUploadUrlDto createFileUploadUrlDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){ // 유효성 검사
            throw new CustomException(bindingResult);
        }
        
        return ApiResponse.createSuccess(fileService.getPreSignedUrl(createFileUploadUrlDto));
    }
    
    
}
