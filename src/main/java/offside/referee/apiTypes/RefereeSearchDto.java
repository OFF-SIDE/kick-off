package offside.referee.apiTypes;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import offside.CategoryEnum;
import offside.LocationEnum;
import offside.StatusEnum;

@Getter
@Setter
public class RefereeSearchDto {
    
    @NotNull
    Boolean isHiring;
    @NotEmpty
    List<LocationEnum> locationList;
    @NotNull
    public LocalDateTime startDate;
    @NotNull
    public LocalDateTime endDate;
    @NotNull
    public Integer startTime;
    @NotNull
    public Integer endTime;
    @NotNull
    CategoryEnum category;
    
    StatusEnum status = StatusEnum.진행중;
    
    public RefereeSearchDto() {}
    
    public RefereeSearchDto(Boolean isHiring, List<LocationEnum> locationList,
        LocalDateTime startDate,
        LocalDateTime endDate, Integer startTime, Integer endTime, CategoryEnum category,
        StatusEnum status) {
        this.isHiring = isHiring;
        this.locationList = locationList;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.category = category;
        this.status = status;
    }
}
