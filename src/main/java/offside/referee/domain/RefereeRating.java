package offside.referee.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.ColumnDefault;

public class RefereeRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public Integer userId;
    
    @ColumnDefault("0")
    public Integer totalRating;
    @ColumnDefault("0")
    public Integer ratingPeople;
    
    public RefereeRating() {}
    public RefereeRating(Integer userId) {
        this.userId = userId;
    }
    
    public RefereeRating(Integer userId, Integer totalRating, Integer ratingPeople) {
        this.userId = userId;
        this.totalRating = totalRating;
        this.ratingPeople = ratingPeople;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public Integer getTotalRating() {
        return totalRating;
    }
    
    public void setTotalRating(Integer totalRating) {
        this.totalRating = totalRating;
    }
    
    public Integer getRatingPeople() {
        return ratingPeople;
    }
    
    public void setRatingPeople(Integer ratingPeople) {
        this.ratingPeople = ratingPeople;
    }
}
