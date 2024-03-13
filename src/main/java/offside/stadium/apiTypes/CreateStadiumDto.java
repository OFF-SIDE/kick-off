package offside.stadium.apiTypes;

import lombok.Getter;
import lombok.Setter;
import offside.LocationEnum;
import offside.StadiumCategoryEnum;

@Getter
@Setter
public class CreateStadiumDto {
    public String name;
    public LocationEnum location;
    public String contactPhone;
    public String comment;
    public StadiumCategoryEnum category;
    public String price;
    public String image;
    public float x;
    public float y;
    public String openAt;
    public String closeAt;
    
    public CreateStadiumDto(){}
    
    public CreateStadiumDto(String name, LocationEnum location, String contactPhone, String comment,
        StadiumCategoryEnum category, String price, String image, float x, float y, String openAt,
        String closeAt) {
        this.name = name;
        this.location = location;
        this.contactPhone = contactPhone;
        this.comment = comment;
        this.category = category;
        this.price = price;
        this.image = image;
        this.x = x;
        this.y = y;
        this.openAt = openAt;
        this.closeAt = closeAt;
    }
}

