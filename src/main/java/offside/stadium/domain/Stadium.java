package offside.stadium.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import offside.LocationEnum;
import offside.StadiumCategoryEnum;
import offside.stadium.apiTypes.CreateStadiumDto;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
public class Stadium{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocationEnum location;
    private String contactPhone;
    private String name;
    @Column(length = 1024)
    private String comment;
    
    private StadiumCategoryEnum category;
    private String image;
    private String price;

    @ColumnDefault("0")
    private int totalRating;
    @ColumnDefault("0")
    private int ratingPeople;
    @ColumnDefault("0")
    private int visitor;

    private float x;
    private float y;
    private String openAt;
    private String closeAt;

    public Stadium(CreateStadiumDto createStadiumDto) {
         this.location = createStadiumDto.getLocation();
         this.contactPhone = createStadiumDto.getContactPhone();
         this.name = createStadiumDto.getName();
         this.comment = createStadiumDto.getComment();
         this.category = createStadiumDto.getCategory();
         this.image = createStadiumDto.getImage();
         this.price = createStadiumDto.getPrice();
         this.x = createStadiumDto.getX();
         this.y = createStadiumDto.getY();
         this.openAt = createStadiumDto.getOpenAt();
         this.closeAt = createStadiumDto.getCloseAt();
     }
     public Stadium(){}
}
