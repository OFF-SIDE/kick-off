package offside.stadium.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import offside.stadium.apiTypes.CreateStadiumByCrawlerDto;
import offside.stadium.apiTypes.CreateStadiumDto;
import offside.stadium.apiTypes.CreateStadiumInfoDto;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class Stadium{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String location;
    private String contactPhone;
    private String name;
    @Column(length = 512)
    private String comment;
    
    private String category;
    private String image;
    private String price;
    private int totalRating;
    private int ratingPeople;
    
    private int visitor;
    private float X;
    private float Y;
    private String openAt;
    private String closeAt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }

    public int getRatingPeople() {
        return ratingPeople;
    }

    public void setRatingPeople(int ratingPeople) {
        this.ratingPeople = ratingPeople;
    }

    public int getVisitor() {
        return visitor;
    }

    public void setVisitor(int visitor) {
        this.visitor = visitor;
    }

    public float getX() {
        return X;
    }

    public void setX(float x) {
        X = x;
    }

    public float getY() {
        return Y;
    }

    public void setY(float y) {
        Y = y;
    }

    public String getOpenAt() {
        return openAt;
    }

    public void setOpenAt(String openAt) {
        this.openAt = openAt;
    }

    public String getCloseAt() {
        return closeAt;
    }

    public void setCloseAt(String closeAt) {
        this.closeAt = closeAt;
    }

    public Stadium(CreateStadiumDto createStadiumDto) {
        this.location = createStadiumDto.getLocation();
        this.contactPhone = createStadiumDto.getContactPhone();
        this.name = createStadiumDto.getName();
        this.comment = createStadiumDto.getComment();
        this.category = createStadiumDto.getCategory();
        this.image = createStadiumDto.getImage();
        this.price = createStadiumDto.getPrice();
        this.X = createStadiumDto.getX();
        this.Y = createStadiumDto.getY();
        this.openAt = createStadiumDto.getOpenAt();
        this.closeAt = createStadiumDto.getCloseAt();
    }

    public Stadium(){}
}
