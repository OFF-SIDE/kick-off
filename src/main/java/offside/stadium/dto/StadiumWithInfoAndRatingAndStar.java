package offside.stadium.dto;

import java.util.List;
import offside.stadium.domain.Stadium;
import offside.stadium.domain.StadiumInfo;
import offside.stadium.domain.StadiumRating;
import offside.stadium.domain.StadiumStar;

public class StadiumWithInfoAndRatingAndStar {
    public Stadium stadium;
    public List<StadiumInfo> stadiumInfoList;
    public List<StadiumRating> stadiumRateList;
    public boolean stadiumStar;
    
    public StadiumWithInfoAndRatingAndStar() {}
    
    public StadiumWithInfoAndRatingAndStar(Stadium stadium, List<StadiumInfo> stadiumInfoList,
        List<StadiumRating> stadiumRateList, boolean stadiumStar) {
        this.stadium = stadium;
        this.stadiumInfoList = stadiumInfoList;
        this.stadiumRateList = stadiumRateList;
        this.stadiumStar = stadiumStar;
    }
}
