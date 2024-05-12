package offside.Notice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import jakarta.persistence.GenerationType;
import java.time.ZoneId;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    
    public String title;
    public LocalDateTime createdAt;
    public String comment;
    
    public Notice(){}
    public Notice(String title, String comment){
        this.title = title;
        this.comment = comment;
        this.createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
