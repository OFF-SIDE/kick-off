package offside.referee.apiTypes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import offside.CategoryEnum;
import offside.LocationEnum;

@Getter
@Setter
public class CreateRefereeHiringDto {
    @NotNull
    public Integer userId;
    @NotBlank
    public String title;
    @NotNull
    public Integer price;
    
    @NotNull
    public Integer stadiumId;
    
    @NotBlank
    public String userStadium;
    @NotBlank
    public String comment;
    
    public Boolean dateNego = false;
    public Boolean timeNego = false;
    public Boolean priceNego = false;
    
    @NotEmpty
    public List<CategoryEnum> categoryList;
    @NotEmpty
    public List<LocationEnum> locationList;
    @NotEmpty
    public List<Integer> dateList;
    @NotEmpty
    public List<Integer> timeList;
    
    public CreateRefereeHiringDto() {}
    
    public CreateRefereeHiringDto(Integer userId, String title, Integer price, Integer stadiumId,
        String userStadium, String comment, List<CategoryEnum> categoryList,
        List<LocationEnum> locationList, List<Integer> dateList, List<Integer> timeList) {
        this.userId = userId;
        this.title = title;
        this.price = price;
        this.stadiumId = stadiumId;
        this.userStadium = userStadium;
        this.comment = comment;
        this.categoryList = categoryList;
        this.locationList = locationList;
        this.dateList = dateList;
        this.timeList = timeList;
    }
    
    public CreateRefereeHiringDto(Integer userId, String title, Integer price, Integer stadiumId,
        String userStadium, String comment, Boolean dateNego, Boolean timeNego, Boolean priceNego,
        List<CategoryEnum> categoryList, List<LocationEnum> locationList, List<Integer> dateList,
        List<Integer> timeList) {
        this.userId = userId;
        this.title = title;
        this.price = price;
        this.stadiumId = stadiumId;
        this.userStadium = userStadium;
        this.comment = comment;
        this.dateNego = dateNego;
        this.timeNego = timeNego;
        this.priceNego = priceNego;
        this.categoryList = categoryList;
        this.locationList = locationList;
        this.dateList = dateList;
        this.timeList = timeList;
    }
}
