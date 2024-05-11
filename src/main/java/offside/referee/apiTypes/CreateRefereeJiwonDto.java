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
    @NotNull(message = "userId를 입력해주세요")
    public Integer userId;
    @NotNull(message = "category를 입력해주세요")
    public CategoryEnum category;
    @NotEmpty(message = "location을 입력해주세요")
    public List<LocationEnum> locationList;
    @NotNull(message = "startDate를 입력해주세요")
    public LocalDateTime startDate;
    @NotNull(message = "endDate를 입력해주세요")
    public LocalDateTime endDate;
    
    public Boolean dateNego = false;
    @NotNull(message = "startTime을 입력해주세요")
    public Integer startTime;
    @NotNull(message = "endTime을 입력해주세요")
    public Integer endTime;
    public Boolean timeNego = false;
    @NotNull(message = "price를 입력해주세요")
    public Integer price;
    public Boolean priceNego = false;
    public String imgLink;
    @NotBlank(message = "title을 입력해주세요")
    public String title;
    @NotBlank(message = "comment를 입력해주세요")
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
