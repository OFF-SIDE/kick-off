package offside.file.apiTypes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import offside.file.FileTypeEnum;

/**
 * @summary S3 업로드 URL를 만들기 위해 필요한 데이터
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFileUploadUrlDto {
    @NotNull(message = "filetype을 입력해주세요")
    FileTypeEnum fileType;
    @NotBlank(message = "filename을 입력해주세요")
    String fileName;
}
