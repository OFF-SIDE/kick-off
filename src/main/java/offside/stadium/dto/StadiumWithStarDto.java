package offside.stadium.dto;

import offside.stadium.domain.Stadium;

public class StadiumWithStarDto {
    public Stadium stadium;
    public boolean isStar = false;

    public StadiumWithStarDto(){}
    public StadiumWithStarDto(Stadium stadium, boolean isStar){
        this.stadium = stadium;
        this.isStar = isStar;
    }
}
