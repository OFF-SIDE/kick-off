package offside.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import offside.CategoryEnum;
import offside.LocationEnum;

@Getter
@Setter
public class UpdateUserInfoDto {
    @NotNull(message = "location을 입력해주세요")
    private LocationEnum location;
    @NotNull(message = "category를 입력해주세요")
    private CategoryEnum category;
    public Integer id;
}
