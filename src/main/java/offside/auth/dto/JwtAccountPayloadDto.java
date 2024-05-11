package offside.auth.dto;

import lombok.Getter;
import lombok.Setter;
import offside.CategoryEnum;
import offside.LocationEnum;

@Getter
@Setter
public class JwtAccountPayloadDto {
    Integer id;
    String nickname;
    LocationEnum location;
    CategoryEnum category;
    
    public JwtAccountPayloadDto(Integer id, String nickname, LocationEnum location,
        CategoryEnum category) {
        this.id = id;
        this.nickname = nickname;
        this.location = location;
        this.category = category;
    }
}
