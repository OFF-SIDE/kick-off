package offside.referee.apiTypes;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefereeUserRequestDto {
    @NotNull
    Integer userId;
    public RefereeUserRequestDto(){}
    public RefereeUserRequestDto(Integer userId) {
        this.userId = userId;
    }
}
