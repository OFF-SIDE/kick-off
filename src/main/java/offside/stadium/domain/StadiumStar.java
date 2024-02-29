package offside.stadium.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class StadiumStar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public Integer userId;
    public Integer stadiumId;
    
    public StadiumStar() {}
    
    public StadiumStar(Integer userId, Integer stadiumId) {
        this.userId = userId;
        this.stadiumId = stadiumId;
    }
}
