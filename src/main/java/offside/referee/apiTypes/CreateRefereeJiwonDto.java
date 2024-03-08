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
public class CreateRefereeJiwonDto {
    @NotNull
    public Integer userId;
    @NotEmpty
    public List<CategoryEnum> categoryList;
    @NotEmpty
    public List<LocationEnum> locationList;
    @NotEmpty
    public List<Integer> dateList;
    
    public Boolean dateNego = false;
    @NotEmpty
    public List<Integer> timeList;
    public Boolean timeNego = false;
    @NotNull
    public Integer price;
    public Boolean priceNego = false;
    public String imgLink;
    @NotBlank
    public String title;
    @NotBlank
    public String comment;
    
    public CreateRefereeJiwonDto() {}
    
    public CreateRefereeJiwonDto(Integer userId, List<CategoryEnum> categoryList,
        List<LocationEnum> locationList, List<Integer> dateList, Boolean dateNego,
        List<Integer> timeList, Boolean timeNego, Integer price, Boolean priceNego,
        String imgLink, String title, String comment) {
        this.userId = userId;
        this.categoryList = categoryList;
        this.locationList = locationList;
        this.dateList = dateList;
        this.dateNego = dateNego;
        this.timeList = timeList;
        this.timeNego = timeNego;
        this.price = price;
        this.priceNego = priceNego;
        this.imgLink = imgLink;
        this.title = title;
        this.comment = comment;
    }
}
