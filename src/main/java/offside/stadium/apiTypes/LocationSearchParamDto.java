package offside.stadium.apiTypes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import offside.LocationEnum;
import offside.StadiumCategoryEnum;

@Getter
@Setter
public class LocationSearchParamDto{
    @NotNull(message = "종목을 선택해주세요!")
    StadiumCategoryEnum category;
    @NotNull(message = "지역구를 선택해주세요!")
    List<LocationEnum> location;
    public Integer userId;
}
