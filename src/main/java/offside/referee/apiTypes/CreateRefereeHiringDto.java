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
    
    @NotNull
    public CategoryEnum category;
    @NotNull
    public LocationEnum location;
    @NotNull
    public LocalDateTime startDate;
    @NotNull
    public LocalDateTime endDate;
    
    @NotNull
    public Integer startTime;
    @NotNull
    public Integer endTime;
    
    public CreateRefereeHiringDto() {}
    
    public CreateRefereeHiringDto(Integer userId, String title, Integer price, Integer stadiumId,
        String userStadium, String comment, Boolean dateNego, Boolean timeNego, Boolean priceNego,
        CategoryEnum category, LocationEnum location, LocalDateTime startDate,
        LocalDateTime endDate,
        Integer startTime, Integer endTime) {
        this.userId = userId;
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
