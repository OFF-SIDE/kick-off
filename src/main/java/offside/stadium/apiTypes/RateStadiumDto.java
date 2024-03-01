package offside.stadium.apiTypes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RateStadiumDto {
    @NotNull
    
    public Integer userId;
    @NotNull
    public Integer rating;
    @NotBlank
    public String comment;
}
