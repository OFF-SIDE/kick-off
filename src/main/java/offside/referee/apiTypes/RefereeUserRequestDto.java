package offside.referee.apiTypes;

import jakarta.validation.constraints.NotNull;

public class RefereeUserRequestDto {
    @NotNull
    Integer userId;
    public RefereeUserRequestDto(){}
    public RefereeUserRequestDto(Integer userId) {
        this.userId = userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
}
