package offside.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import offside.CategoryEnum;
import offside.LocationEnum;

@Getter
@Setter
@AllArgsConstructor
public class JwtAccountPayloadDto {
    Integer id;
    String name;
    String nickname;
    LocationEnum location;
    CategoryEnum category;
    String profileImage;
}
