package offside.stadium.apiTypes;

public class CreateStadiumDto {
    public String name;
    public String location;
    public String contactPhone;
    public String comment;
    public String category;
    public String price;
    public String image;
    public float x;
    public float y;
    public String openAt;
    public String closeAt;
    
    public CreateStadiumDto(String name, String location, String contactPhone, String comment,
        String category, String price, String image, float x, float y, String openAt,
        String closeAt) {
        this.name = name;
        this.location = location;
        this.contactPhone = contactPhone;
        this.comment = comment;
        this.category = category;
        this.price = price;
        this.image = image;
        this.x = x;
        this.y = y;
        this.openAt = openAt;
        this.closeAt = closeAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        y = y;
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
}

