package offside.referee.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
@Entity
@Getter
@Setter
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
    
}
