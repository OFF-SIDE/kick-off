package offside.referee.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RefereeTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    
    public Integer refereeId;
    public Integer hour; // 1430 1200
    
    public RefereeTime() {}
    
    public RefereeTime(Integer refereeId, Integer hour) {
        this.refereeId = refereeId;
        this.hour = hour;
    }
    
}

