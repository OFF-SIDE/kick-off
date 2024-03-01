package offside.stadium.apiTypes;

import jakarta.validation.constraints.NotNull;

public class UserRequestDto {
    @NotNull
    Integer userId;
    
    public UserRequestDto() {}
    
    public UserRequestDto(Integer userId) {
        this.userId = userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public Integer getUserId() {
        return userId;
    }
}
