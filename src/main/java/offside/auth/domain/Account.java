package offside.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.Getter;
import lombok.Setter;
import offside.CategoryEnum;
import offside.LocationEnum;
import offside.auth.SocialProviderEnum;
import offside.auth.apiTypes.SocialSignupDto;

@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    
    @Column(nullable = false, unique = true)
    public String oauthId;
    @Column(nullable = false)
    public SocialProviderEnum socialProvider;
    
    @Column(nullable = false)
    public String name;
    
    @Column(nullable = false)
    public String nickname;
    
    @Column(nullable = false)
    public LocationEnum location;
    @Column(nullable = false)
    public CategoryEnum category;
    
    @Column(nullable = false)
    public String profileImage;
    
    @Column(nullable = false)
    public LocalDateTime createdAt;
    
    
    public Account() {}
    
    public Account(String oauthId, SocialProviderEnum socialProvider, String name,
        String nickname, LocationEnum location, CategoryEnum category, String profileImage) {
        this.oauthId = oauthId;
        this.socialProvider = socialProvider;
        this.name = name;
        this.nickname = nickname;
        this.location = location;
        this.category = category;
        this.profileImage = profileImage;
        this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
    
    public Account(SocialSignupDto socialSignupDto){
        this.oauthId = socialSignupDto.getOauthId();
        this.socialProvider = socialSignupDto.getSocialProvider();
        this.name = socialSignupDto.getName();
        this.nickname = socialSignupDto.getNickname();
        this.location = socialSignupDto.getLocation();
        this.category = socialSignupDto.getCategory();
        this.profileImage = socialSignupDto.getProfileImage() == null ? "default" : socialSignupDto.getProfileImage(); // TODO : default 이미지 링크 추가
        this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
