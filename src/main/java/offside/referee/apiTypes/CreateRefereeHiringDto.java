package offside.referee.apiTypes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import offside.CategoryEnum;
import offside.LocationEnum;

@Getter
@Setter
public class CreateRefereeHiringDto {
    @NotBlank(message = "title을 입력해주세요")
    public String title;
    @NotNull(message = "price를 입력해주세요")
    public Integer price;
    
    @NotNull(message = "stadiumId를 입력해주세요")
    public Integer stadiumId;
    
    @NotBlank(message = "userStadium을 입력해주세요")
    public String userStadium;
    @NotBlank(message = "comment를 입력해주세요")
    public String comment;
    
    public Boolean dateNego = false;
    public Boolean timeNego = false;
    public Boolean priceNego = false;
    
    @NotNull(message = "category를 입력해주세요")
    public CategoryEnum category;
    @NotNull(message = "location을 입력해주세요")
    public LocationEnum location;
    @NotNull(message = "startDate를 입력해주세요")
    public LocalDateTime startDate;
    @NotNull(message = "endDate를 입력해주세요")
    public LocalDateTime endDate;
    
    @NotNull(message = "startTime을 입력해주세요")
    public Integer startTime;
    @NotNull(message = "endTime을 입력해주세요")
    public Integer endTime;
    
    public CreateRefereeHiringDto() {}
    
    public CreateRefereeHiringDto(String title, Integer price, Integer stadiumId,
        String userStadium, String comment, Boolean dateNego, Boolean timeNego, Boolean priceNego,
        CategoryEnum category, LocationEnum location, LocalDateTime startDate,
        LocalDateTime endDate,
        Integer startTime, Integer endTime) {
        this.title = title;
        this.price = price;
        this.stadiumId = stadiumId;
        this.userStadium = userStadium;
        this.comment = comment;
        this.dateNego = dateNego;
        this.timeNego = timeNego;
        this.priceNego = priceNego;
        this.category = category;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
