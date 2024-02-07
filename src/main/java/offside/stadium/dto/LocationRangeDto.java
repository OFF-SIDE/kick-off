package offside.stadium.dto;

public class LocationRangeDto {
    String startX;
    String startY;
    
    String endX;
    String endY;
    
    public LocationRangeDto(String startX, String startY, String endX, String endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }
}
