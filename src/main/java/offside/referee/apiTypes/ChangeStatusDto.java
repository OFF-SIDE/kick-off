package offside.referee.apiTypes;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import offside.StatusEnum;

@Getter
@Setter
public class ChangeStatusDto {
    @NotNull(message = "status를 입력해주세요")
    StatusEnum status;
    
    public ChangeStatusDto() {}
    
    public ChangeStatusDto(StatusEnum status) {
        this.status = status;
    }
}
