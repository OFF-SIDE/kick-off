package offside.referee.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import offside.CategoryEnum;
import offside.StatusEnum;
import offside.referee.apiTypes.CreateRefereeHiringDto;
import offside.referee.apiTypes.CreateRefereeJiwonDto;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Setter
public class Referee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    
    public Integer userId;
    public String title;
    public Integer price;
    
    public Integer stadiumId;
    @Column(nullable = true)
    public String userStadium;
    
    @Column(nullable = true)
    public String imgLink; // 지원글
    public String comment;
    public StatusEnum status;
    public Boolean isHiring;
    public CategoryEnum category;
    
    @ColumnDefault("false")
    public Boolean dateNego;
    @ColumnDefault("false")
    public Boolean timeNego;
    @ColumnDefault("false")
    public Boolean priceNego;
    public LocalDateTime createdAt;
    
    
    public Referee() {}
    
    public Referee(Integer id, Integer userId, String title, Integer price, Integer stadiumId,
        String userStadium, String imgLink, String comment, StatusEnum status, Boolean isHiring,
        CategoryEnum category, Boolean dateNego, Boolean timeNego, Boolean priceNego,
        LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.price = price;
        this.stadiumId = stadiumId;
        this.userStadium = userStadium;
        this.imgLink = imgLink;
        this.comment = comment;
        this.status = status;
        this.isHiring = isHiring;
        this.category = category;
        this.dateNego = dateNego;
        this.timeNego = timeNego;
        this.priceNego = priceNego;
        this.createdAt = createdAt;
    }
    
    public Referee(CreateRefereeHiringDto createRefereeHiringDto){
        this.userId = createRefereeHiringDto.getUserId();
        this.title = createRefereeHiringDto.getTitle();
        this.price = createRefereeHiringDto.getPrice();
        this.imgLink = "";
        this.comment = createRefereeHiringDto.getComment();
        this.status = StatusEnum.진행중;
        this.dateNego = createRefereeHiringDto.getDateNego();
        this.timeNego = createRefereeHiringDto.getTimeNego();
        this.priceNego = createRefereeHiringDto.getPriceNego();
        this.isHiring = true;
        this.userStadium = createRefereeHiringDto.getUserStadium();
        this.stadiumId = createRefereeHiringDto.getStadiumId();
        this.createdAt = LocalDateTime.now();
        this.category = createRefereeHiringDto.getCategory();
    }
    
    public Referee(CreateRefereeJiwonDto createRefereeJiwonDto){
        this.userId = createRefereeJiwonDto.getUserId();
        this.title = createRefereeJiwonDto.getTitle();
        this.price = createRefereeJiwonDto.getPrice();
        this.imgLink = createRefereeJiwonDto.getImgLink();
        this.comment = createRefereeJiwonDto.getComment();
        this.status = StatusEnum.진행중;
        this.dateNego = createRefereeJiwonDto.getDateNego();
        this.timeNego = createRefereeJiwonDto.getTimeNego();
        this.priceNego = createRefereeJiwonDto.getPriceNego();
        this.isHiring = false;
        this.createdAt = LocalDateTime.now();
        this.category = createRefereeJiwonDto.getCategory();
    }
    
}
