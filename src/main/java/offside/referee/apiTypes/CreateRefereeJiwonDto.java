package offside.referee.apiTypes;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
    @NotNull
    public CategoryEnum category;
    @NotEmpty
    public List<LocationEnum> locationList;
    @NotNull
    public LocalDateTime startDate;
    @NotNull
    public LocalDateTime endDate;
    
    public Boolean dateNego = false;
    @NotNull
    public Integer startTime;
    @NotNull
    public Integer endTime;
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
    
    public CreateRefereeJiwonDto(Integer userId, CategoryEnum category,
        List<LocationEnum> locationList,
        LocalDateTime startDate, LocalDateTime endDate, Boolean dateNego, Integer startTime,
        Integer endTime, Boolean timeNego, Integer price, Boolean priceNego, String imgLink,
        String title, String comment) {
        this.userId = userId;
        this.category = category;
        this.locationList = locationList;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dateNego = dateNego;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeNego = timeNego;
        this.price = price;
        this.priceNego = priceNego;
        this.imgLink = imgLink;
        this.title = title;
        this.comment = comment;
    }
}
