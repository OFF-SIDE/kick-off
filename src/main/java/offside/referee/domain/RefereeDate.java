package offside.referee.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;

@Entity
public class RefereeDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    
    public Integer refereeId;
    public Date date;
    
    public RefereeDate() {}
    
    public RefereeDate(Integer refereeId, Date date) {
        this.refereeId = refereeId;
        this.date = date;
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
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
}
