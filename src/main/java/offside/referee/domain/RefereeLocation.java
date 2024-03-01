package offside.referee.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import offside.LocationEnum;

@Entity
public class RefereeLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    
    public Integer refereeId;
    public LocationEnum location;
    
    public RefereeLocation() {
    }
    
    public RefereeLocation(Integer refereeId, LocationEnum location) {
        this.refereeId = refereeId;
        this.location = location;
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
    
    public LocationEnum getLocation() {
        return location;
    }
    
    public void setLocation(LocationEnum location) {
        this.location = location;
    }
}
