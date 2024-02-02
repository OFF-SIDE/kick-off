package offside.stadium.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
}