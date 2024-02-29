package offside.stadium.dto;

import java.util.List;
import offside.stadium.domain.Stadium;
import offside.stadium.domain.StadiumRating;

public class StadiumWithRatingDto {
    public Stadium stadium;
    public List<StadiumRating> stadiumRateList;
    
    public StadiumWithRatingDto(Stadium stadium, List<StadiumRating> stadiumRateList) {
        this.stadium = stadium;
        this.stadiumRateList = stadiumRateList;
    }
}
