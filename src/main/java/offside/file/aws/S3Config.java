package offside.file.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class S3Config {
    @Value("${AWS_S3_SECRET_ACCESS_KEY}")
    private String secretKey;
    @Value("${AWS_S3_ACCESS_KEY}")
    private String accessKey;
    @Value("${AWS_REGION}")
    private String region;
    
//    @Bean
//    public AmazonS3 amazonS3Client() {
//        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
//        return  AmazonS3ClientBuilder.standard()
//            .withRegion(region)
//            .enablePathStyleAccess()
//            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
//            .build();
//    }
    @Bean
    @Primary
    public BasicAWSCredentials awsCredentialsProvider(){
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return basicAWSCredentials;
    }
    
    @Bean
    public AmazonS3 amazonS3() {
        AmazonS3 s3Builder = AmazonS3ClientBuilder.standard()
            .withRegion(region)
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentialsProvider()))
            .build();
        return s3Builder;
    }
}
