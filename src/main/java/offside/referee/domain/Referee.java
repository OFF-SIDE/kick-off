package offside.referee.domain;

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
import offside.StatusEnum;
import offside.referee.apiTypes.CreateRefereeHiringDto;
import offside.referee.apiTypes.CreateRefereeJiwonDto;
import org.hibernate.annotations.ColumnDefault;

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
    public String imgLink;
    public String comment;
    public StatusEnum status;
    public Boolean isHiring;
    public CategoryEnum category;
    
    public LocalDateTime startDate;
    public LocalDateTime endDate;
    @ColumnDefault("false")
    public Boolean dateNego;
    
    public Integer startTime;
    public Integer endTime;
    @ColumnDefault("false")
    public Boolean timeNego;
    @ColumnDefault("false")
    public Boolean priceNego;
    public LocalDateTime createdAt;
    
    
    public Referee() {}
    
    public Referee(Integer id, Integer userId, String title, Integer price, Integer stadiumId,
        String userStadium, String imgLink, String comment, StatusEnum status, Boolean isHiring,
        CategoryEnum category, LocalDateTime startDate, LocalDateTime endDate, Boolean dateNego,
        Integer startTime, Integer endTime, Boolean timeNego, Boolean priceNego) {
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
        this.startDate = startDate;
        this.endDate = endDate;
        this.dateNego = dateNego;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeNego = timeNego;
        this.priceNego = priceNego;
        this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
    
    public Referee(CreateRefereeHiringDto createRefereeHiringDto) {
        this.userId = createRefereeHiringDto.getUserId();
        this.title = createRefereeHiringDto.getTitle();
        this.price = createRefereeHiringDto.getPrice();
        this.stadiumId = createRefereeHiringDto.getStadiumId();
        this.userStadium = createRefereeHiringDto.getUserStadium();
        this.imgLink = null;
        this.comment = createRefereeHiringDto.getComment();
        this.status = StatusEnum.진행중;
        this.isHiring = true;
        this.category = createRefereeHiringDto.getCategory();
        this.startDate = createRefereeHiringDto.getStartDate();
        this.endDate = createRefereeHiringDto.getEndDate();
        this.dateNego = createRefereeHiringDto.getDateNego();
        this.startTime = createRefereeHiringDto.getStartTime();
        this.endTime = createRefereeHiringDto.getEndTime();
        this.timeNego = createRefereeHiringDto.getTimeNego();
        this.priceNego = createRefereeHiringDto.getPriceNego();
        this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
    
    
    public Referee(CreateRefereeJiwonDto createRefereeJiwonDto) {
        this.userId = createRefereeJiwonDto.getUserId();
        this.title = createRefereeJiwonDto.getTitle();
        this.price = createRefereeJiwonDto.getPrice();
        this.stadiumId = 0;
        this.userStadium = "";
        this.imgLink = createRefereeJiwonDto.getImgLink();
        this.comment = createRefereeJiwonDto.getComment();
        this.status = StatusEnum.진행중;
        this.isHiring = false;
        this.category = createRefereeJiwonDto.getCategory();
        this.startDate = createRefereeJiwonDto.getStartDate();
        this.endDate = createRefereeJiwonDto.getEndDate();
        this.dateNego = createRefereeJiwonDto.getDateNego();
        this.startTime = createRefereeJiwonDto.getStartTime();
        this.endTime = createRefereeJiwonDto.getEndTime();
        this.timeNego = createRefereeJiwonDto.getTimeNego();
        this.priceNego = createRefereeJiwonDto.getPriceNego();
        this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
