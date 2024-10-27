package offside.file.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PresignedUrlDto {
    private String file;
    private String preSignedUrl;
    
}
