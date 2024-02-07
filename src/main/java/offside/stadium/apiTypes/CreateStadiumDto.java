package offside.stadium.apiTypes;

public class CreateStadiumDto {
    
    public String name;
    public String location;
    public String contactPhone;
    public String comment;
    public String category;
    public String price;
    public String image;
    public String X;
    public String Y;
    public String openAt;
    public String closeAt;
    
    public CreateStadiumDto(String name, String location, String contactPhone, String comment,
        String category, String price, String image, String x, String y, String openAt,
        String closeAt) {
        this.name = name;
        this.location = location;
        this.contactPhone = contactPhone;
        this.comment = comment;
        this.category = category;
        this.price = price;
        this.image = image;
        this.X = x;
        this.Y = y;
        this.openAt = openAt;
        this.closeAt = closeAt;
    }
}

