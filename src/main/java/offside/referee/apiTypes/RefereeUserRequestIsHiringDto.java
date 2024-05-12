package offside.referee.apiTypes;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefereeUserRequestIsHiringDto {
    @NotNull
    Integer userId;
    @NotNull
    Integer IsHiring;
    public RefereeUserRequestIsHiringDto(){}
    public RefereeUserRequestIsHiringDto(Integer userId, Integer IsHiring) {
        this.userId = userId;
        this.IsHiring = IsHiring;
    }
}
