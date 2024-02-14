package offside.stadium.apiTypes;

import offside.stadium.domain.Stadium;

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

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getOpenDay() {
        return openDay;
    }

    public void setOpenDay(String openDay) {
        this.openDay = openDay;
    }

    public String getCloseDay() {
        return closeDay;
    }

    public void setCloseDay(String closeDay) {
        this.closeDay = closeDay;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}

