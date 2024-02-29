package offside.stadium.dto;

import java.util.List;
import offside.stadium.domain.Stadium;
import offside.stadium.domain.StadiumInfo;
import offside.stadium.domain.StadiumRating;

public class StadiumWithInfoAndRating{
    public Stadium stadium;
    public List<StadiumInfo> stadiumInfoList;
    public List<StadiumRating> stadiumRateList;
    
    public StadiumWithInfoAndRating(Stadium stadium, List<StadiumInfo> stadiumInfoList,
        List<StadiumRating> stadiumRateList) {
        this.stadium = stadium;
        this.stadiumInfoList = stadiumInfoList;
        this.stadiumRateList = stadiumRateList;
    }
}
