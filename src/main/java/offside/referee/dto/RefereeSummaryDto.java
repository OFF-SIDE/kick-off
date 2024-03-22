package offside.referee.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import offside.CategoryEnum;
import offside.LocationEnum;
import offside.StatusEnum;
import offside.referee.domain.Referee;

@Getter
@Setter
public class RefereeSummaryDto {
    public Integer id;
    public Integer userId;
    public String title;
    public Integer price;
    public StatusEnum status;
    public Boolean isHiring;
    public List<LocationEnum> locationList;
    public LocalDateTime startDate;
    public LocalDateTime endDate;
    public Integer startTime;
    public Integer endTime;
    public Boolean dateNego;
    public Boolean timeNego;
    public Boolean priceNego;
    public LocalDateTime createdAt;
    public CategoryEnum category;
    
    public RefereeSummaryDto() {
    }
    
    public RefereeSummaryDto(Referee referee, List<LocationEnum> locationList) {
        this.id = referee.getId();
        this.userId = referee.getUserId();
        this.title = referee.getTitle();
        this.price = referee.getPrice();
        this.status = referee.getStatus();
        this.isHiring = referee.getIsHiring();
        this.locationList = locationList;
        this.startDate = referee.getStartDate();
        this.endDate = referee.getEndDate();
        this.startTime = referee.getStartTime();
        this.endTime = referee.getEndTime();
        this.dateNego = referee.getDateNego();
        this.timeNego = referee.getTimeNego();
        this.priceNego = referee.getPriceNego();
        this.createdAt = referee.getCreatedAt();
        this.category = referee.getCategory();
    }
    
    public RefereeSummaryDto(Integer id, Integer userId, String title, Integer price,
        StatusEnum status,
        Boolean isHiring, List<LocationEnum> locationList, LocalDateTime startDate,
        LocalDateTime endDate, Integer startTime, Integer endTime, Boolean dateNego,
        Boolean timeNego,
        Boolean priceNego, LocalDateTime createdAt, CategoryEnum category) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.price = price;
        this.status = status;
        this.isHiring = isHiring;
        this.locationList = locationList;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dateNego = dateNego;
        this.timeNego = timeNego;
        this.priceNego = priceNego;
        this.createdAt = createdAt;
        this.category = category;
    }
    
}
