package offside.common.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import offside.common.ArticleEnum;

@Getter
@Setter
@AllArgsConstructor
public class Article {
    private Integer articleId;
    private ArticleEnum articleType;
    private Integer writerId ;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
