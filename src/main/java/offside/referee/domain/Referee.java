package offside.referee.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;
import offside.StatusEnum;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

@Entity
public class Referee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    
    public Integer userId;
    public String title;
    public Integer price;
    public String imgLink;
    public String comment;
    public StatusEnum status;
    @ColumnDefault("false")
    public Boolean dateNego;
    @ColumnDefault("false")
    public Boolean timeNego;
    @ColumnDefault("false")
    public Boolean priceNego;
    @CreatedDate
    public Date createdAt;
    public Referee() {}
    
    public Referee(Integer userId, String title, Integer price, String imgLink, String comment,
        StatusEnum status, Boolean dateNego, Boolean timeNego, Boolean priceNego) {
        this.userId = userId;
        this.title = title;
        this.price = price;
        this.imgLink = imgLink;
        this.comment = comment;
        this.status = status;
        this.dateNego = dateNego;
        this.timeNego = timeNego;
        this.priceNego = priceNego;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Integer getPrice() {
        return price;
    }
    
    public void setPrice(Integer price) {
        this.price = price;
    }
    
    public String getImgLink() {
        return imgLink;
    }
    
    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public StatusEnum getStatus() {
        return status;
    }
    
    public void setStatus(StatusEnum status) {
        this.status = status;
    }
    
    public Boolean getDateNego() {
        return dateNego;
    }
    
    public void setDateNego(Boolean dateNego) {
        this.dateNego = dateNego;
    }
    
    public Boolean getTimeNego() {
        return timeNego;
    }
    
    public void setTimeNego(Boolean timeNego) {
        this.timeNego = timeNego;
    }
    
    public Boolean getPriceNego() {
        return priceNego;
    }
    
    public void setPriceNego(Boolean priceNego) {
        this.priceNego = priceNego;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
