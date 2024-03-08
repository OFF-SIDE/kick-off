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
    
}
