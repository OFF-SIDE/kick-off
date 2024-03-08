package offside.referee.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import offside.CategoryEnum;

@Entity
@Getter
@Setter
public class RefereeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    
    public Integer refereeId;
    public CategoryEnum category;
    
    public RefereeCategory() {}
    
    public RefereeCategory(Integer refereeId, CategoryEnum category) {
        this.refereeId = refereeId;
        this.category = category;
    }
}

