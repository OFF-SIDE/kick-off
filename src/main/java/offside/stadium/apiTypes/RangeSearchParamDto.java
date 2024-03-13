package offside.stadium.apiTypes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import offside.StadiumCategoryEnum;

@Getter
@Setter
public class RangeSearchParamDto {
    
    @NotBlank(message = "종목을 선택해주세요!")
    StadiumCategoryEnum category;
    @NotNull(message = "시작하는 X가 필요합니다.")
    float startX;
    @NotNull(message = "시작하는 Y가 필요합니다.")
    float startY;
    @NotNull(message = "끝나는 X가 필요합니다.")
    float endX;
    @NotNull(message = "끝나는 Y가 필요합니다.")
    float endY;
    
    public RangeSearchParamDto(StadiumCategoryEnum category, float startX, float startY, float endX, float endY) {
        this.category = category;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

}
