package offside.stadium.apiTypes;

public class CreateStadiumInfoDto {
    public String subName;
    public String link;
    public String externalId;
    public String openDay;
    public String closeDay;
    public Boolean status;
    
    public CreateStadiumInfoDto(String subName, String link, String externalId, String openDay,
        String closeDay, Boolean status) {
        this.subName = subName;
        this.link = link;
        this.externalId = externalId;
        this.openDay = openDay;
        this.closeDay = closeDay;
        this.status = status;
    }
}

