package offside.referee.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RefereeStar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    
    public Integer userId;
    public Integer refereeId;
    
    public RefereeStar() {}
    
    public RefereeStar(Integer userId, Integer refereeId) {
        this.userId = userId;
        this.refereeId = refereeId;
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
    
    public Integer getRefereeId() {
        return refereeId;
    }
    
    public void setRefereeId(Integer refereeId) {
        this.refereeId = refereeId;
    }
}
