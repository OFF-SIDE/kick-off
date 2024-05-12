package offside.stadium.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import offside.stadium.apiTypes.RateStadiumDto;

@Entity
public class StadiumRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    
    public Integer userId;
    public Integer stadiumId;
    public Integer rating;
    public String comment;
    
    public StadiumRating(Integer stadiumId, Integer userId, RateStadiumDto rateStadiumDto) {
        this.userId = userId;
        this.stadiumId = stadiumId;
        this.rating = rateStadiumDto.rating;
        this.comment = rateStadiumDto.comment;
    }
    
    public StadiumRating() {}
}
