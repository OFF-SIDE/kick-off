package offside.stadium.apiTypes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NameSearchParamDto {
    public String SearchName;
    public Integer userId;

    public NameSearchParamDto(){}

    public NameSearchParamDto(String SearchName, Integer userId){
        this.SearchName = SearchName;
        this.userId = userId;
    }
}
