package offside.stadium.apiTypes;

import jakarta.validation.constraints.NotBlank;

public class LocationSearchParamDto{
    @NotBlank(message = "종목을 선택해주세요!")
    String category;
    @NotBlank(message = "구를 선택해주세요!")
    String location;
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
}
