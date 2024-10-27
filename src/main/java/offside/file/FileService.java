package offside.file;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import java.net.URL;
import java.util.Date;
import java.util.UUID;
import offside.file.apiTypes.CreateFileUploadUrlDto;
import offside.file.dto.PresignedUrlDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    private final AmazonS3 amazonS3;
    
    @Value("${AWS_S3_BUCKET_NAME}")
    private String BUCKET_NAME;
    
    @Autowired
    public FileService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }
    
    /**
     * presigned url 발급
     * @return presigned url
     */
    public PresignedUrlDto getPreSignedUrl(CreateFileUploadUrlDto createFileUploadUrlDto) {
        final var fileName = createUuidFileName(createFileUploadUrlDto);
        
        GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePreSignedUrlRequest(BUCKET_NAME, fileName);
        URL presignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        
        return new PresignedUrlDto(createFinalFileUrl(fileName),presignedUrl.toString());
    }
    
    /**
     * @summary 파일 업로드 후 해당 파일에 접근할 수 있는 경로
     * @param fileName
     * @return
     */
    public String createFinalFileUrl(String fileName){
        return "https://d2tdwq038x7azf.cloudfront.net/" + fileName;
    }
    
    /**
     * 파일 업로드용(PUT) presigned url 생성
     * @param bucket 버킷 이름
     * @param fileName S3 업로드용 파일 이름
     * @return presigned url
     */
    private GeneratePresignedUrlRequest getGeneratePreSignedUrlRequest(String bucket, String fileName) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
            new GeneratePresignedUrlRequest(bucket, fileName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(getPreSignedUrlExpiration());
        generatePresignedUrlRequest.addRequestParameter(
            Headers.S3_CANNED_ACL,
            CannedAccessControlList.PublicRead.toString());
        return generatePresignedUrlRequest;
    }
    
    /**
     * presigned url 유효 기간 설정
     * @return 유효기간
     */
    private Date getPreSignedUrlExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 2;
        expiration.setTime(expTimeMillis);
        return expiration;
    }
    
    /**
     * @summary 파일명과 파일타입을 통해 새로운 경로 및 Uuid 파일명 생성
     * @return 새로운 경로 및 Uuid 파일명
     **/
    private String createUuidFileName(CreateFileUploadUrlDto createFileUploadUrlDto) {
        String prefix;
        switch (createFileUploadUrlDto.getFileType()) {
            case PROFILE:
                prefix = "profile/";
                break;
            case REFEREE:
                prefix = "referee/";
                break;
            case STADIUM:
                prefix = "stadium/";
                break;
            default:
                prefix = "etc/";
                break;
        }
        final var extention = createFileUploadUrlDto.getFileName().substring(createFileUploadUrlDto.getFileName().lastIndexOf("."));
        
        return prefix + UUID.randomUUID().toString() + extention;
    }
    
}
