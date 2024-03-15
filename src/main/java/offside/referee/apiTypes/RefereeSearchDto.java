package offside.referee.apiTypes;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    List<LocationEnum> locationList;
    @NotNull
    List<Integer> dateList;
    @NotNull
    List<Integer> timeList;
    @NotNull
    CategoryEnum category;
    
    StatusEnum status = StatusEnum.진행중;
    
    public RefereeSearchDto() {}
    
    public RefereeSearchDto(Boolean isHiring, List<LocationEnum> locationList,
        List<Integer> dateList,
        List<Integer> timeList, CategoryEnum categoryList, StatusEnum status) {
        this.isHiring = isHiring;
        this.locationList = locationList;
        this.dateList = dateList;
        this.timeList = timeList;
        this.category = categoryList;
        this.status = status;
    }
}
