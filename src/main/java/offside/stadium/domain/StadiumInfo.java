package offside.stadium.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import offside.stadium.apiTypes.CreateStadiumInfoDto;

@Entity
public class StadiumInfo {
    @Id
    public String externalId; // 외부 API에서 가져오는 구장id

    public int stadiumId; // DB에서 관리되는 stadium의 Id
    public String subName;
    public String link;
    public String openDay;
    public String closeDay;
    public Boolean status;
    
    public StadiumInfo(int stadiumId, CreateStadiumInfoDto createStadiumInfoData){
        this.externalId = createStadiumInfoData.getExternalId();
        this.stadiumId = stadiumId;
        this.subName = createStadiumInfoData.getSubName();
        this.link = createStadiumInfoData.getLink();
        this.openDay = createStadiumInfoData.getOpenDay();
        this.closeDay = createStadiumInfoData.getCloseDay();
        this.status = createStadiumInfoData.getStatus();
    }
    
    public StadiumInfo(){}
}
