package offside.stadium.apiTypes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class SearchParamDto {
    
    @NotBlank(message = "종목을 선택해주세요!")
    String category;
    
    @NotNull(message = "시작하는 X가 필요합니다.")
    float startX;
    @NotNull(message = "시작하는 Y가 필요합니다.")
    float startY;
    @NotNull(message = "끝나는 X가 필요합니다.")
    float endX;
    @NotNull(message = "끝나는 Y가 필요합니다.")
    float endY;
    
    public SearchParamDto(String category, float startX, float startY, float endX, float endY) {
        this.category = category;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public float getEndX() {
        return endX;
    }

    public void setEndX(float endX) {
        this.endX = endX;
    }

    public float getEndY() {
        return endY;
    }

    public void setEndY(float endY) {
        this.endY = endY;
    }
}
