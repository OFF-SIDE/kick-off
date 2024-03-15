package offside.referee.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import offside.CategoryEnum;
import offside.LocationEnum;
import offside.StatusEnum;
import offside.referee.domain.Referee;

// 리스트에 보일 내용
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
    public List<Integer> dateList;
    public List<Integer> timeList;
    public Boolean dateNego;
    public Boolean timeNego;
    public Boolean priceNego;
    public LocalDateTime createdAt;
    public CategoryEnum category;
    
    public RefereeSummaryDto(Referee referee, List<LocationEnum> locationList,List<Integer> dateList, List<Integer> timeList) {
        this.id = referee.getId();
        this.userId = referee.getUserId();
        this.title = referee.getTitle();
        this.price = referee.getPrice();
        this.status = referee.getStatus();
        this.isHiring = referee.getIsHiring();
        this.dateNego = referee.getDateNego();
        this.timeNego = referee.getTimeNego();
        this.priceNego = referee.getPriceNego();
        this.createdAt = referee.getCreatedAt();
        this.locationList = locationList;
        this.dateList = dateList;
        this.timeList = timeList;
        this.category = referee.getCategory();
    }
    
    public RefereeSummaryDto() {
    }
    
    public RefereeSummaryDto(Integer id, Integer userId, String title, Integer price,
        StatusEnum status,
        Boolean isHiring, List<LocationEnum> locationList, List<Integer> dateList,
        List<Integer> timeList, Boolean dateNego, Boolean timeNego, Boolean priceNego,
        LocalDateTime createdAt, CategoryEnum category) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.price = price;
        this.status = status;
        this.isHiring = isHiring;
        this.locationList = locationList;
        this.dateList = dateList;
        this.timeList = timeList;
        this.dateNego = dateNego;
        this.timeNego = timeNego;
        this.priceNego = priceNego;
        this.createdAt = createdAt;
        this.category = category;
    }
}
