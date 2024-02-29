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
    
    public Integer getStadiumId() {
        return stadiumId;
    }
    
    public void setStadiumId(Integer stadiumId) {
        this.stadiumId = stadiumId;
    }
}
