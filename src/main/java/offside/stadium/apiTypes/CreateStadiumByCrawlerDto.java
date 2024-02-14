package offside.stadium.apiTypes;

import java.util.List;

public class CreateStadiumByCrawlerDto {
    
    public CreateStadiumDto stadium;
    public List<CreateStadiumInfoDto> stadiumInfoList;
    
    public CreateStadiumByCrawlerDto(CreateStadiumDto stadium,
        List<CreateStadiumInfoDto> stadiumInfoList) {
        this.stadium = stadium;
        this.stadiumInfoList = stadiumInfoList;
    }
}

