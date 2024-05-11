package offside.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import offside.CategoryEnum;
import offside.LocationEnum;
import offside.auth.SocialProviderEnum;

@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    
    @Column(nullable = false)
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
    
    public String profileImage;
    
    @Column(nullable = false)
    public LocalDateTime createdAt;
    
    
    public Account() {}
    
    public Account(Integer id, String oauthId, SocialProviderEnum socialProvider, String name,
        String nickname, LocationEnum location, CategoryEnum category, String profileImage,
        LocalDateTime createdAt) {
        this.id = id;
        this.oauthId = oauthId;
        this.socialProvider = socialProvider;
        this.name = name;
        this.nickname = nickname;
        this.location = location;
        this.category = category;
        this.profileImage = profileImage;
        this.createdAt = createdAt;
    }
}
