package offside.stadium.apiTypes;

public class SearchParamDto {
    String category;
    String startX;
    String startY;
    
    String endX;
    String endY;
    
    public SearchParamDto(String category, String startX, String startY, String endX, String endY) {
        this.category = category;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }
}
