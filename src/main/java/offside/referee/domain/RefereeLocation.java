package offside.referee.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import offside.LocationEnum;

@Entity
@Getter
@Setter
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
}
