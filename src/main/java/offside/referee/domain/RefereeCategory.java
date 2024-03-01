package offside.referee.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import offside.CategoryEnum;

@Entity
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
    
    public CategoryEnum getCategory() {
        return category;
    }
    
    public void setCategory(CategoryEnum category) {
        this.category = category;
    }
}

