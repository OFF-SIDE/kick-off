package offside.referee.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RefereeTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    
    public Integer refereeId;
    public Integer hour;
    public Boolean min;
    
    public RefereeTime() {}
    
    public RefereeTime(Integer refereeId, Integer hour, Boolean min) {
        this.refereeId = refereeId;
        this.hour = hour;
        this.min = min;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getRefereeId() {
        return refereeId;
    }
    
    public void setRefereeId(Integer refereeId) {
        this.refereeId = refereeId;
    }
    
    public Integer getHour() {
        return hour;
    }
    
    public void setHour(Integer hour) {
        this.hour = hour;
    }
    
    public Boolean getMin() {
        return min;
    }
    
    public void setMin(Boolean min) {
        this.min = min;
    }
}

