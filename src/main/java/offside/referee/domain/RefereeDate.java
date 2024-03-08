package offside.referee.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RefereeDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    
    public Integer refereeId;
    public Integer date;
    
    public RefereeDate() {}
    
    public RefereeDate(Integer refereeId, Integer date) {
        this.refereeId = refereeId;
        this.date = date;
    }
}
