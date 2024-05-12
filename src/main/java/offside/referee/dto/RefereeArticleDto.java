package offside.referee.dto;

import java.time.LocalDateTime;
import offside.common.ArticleEnum;
import offside.common.dto.Article;
import offside.referee.domain.Referee;

public class RefereeArticleDto extends Article {
    private Integer articleId;
    private ArticleEnum articleType = ArticleEnum.REFEREE;
    private Integer writerId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    
    public RefereeArticleDto(Referee referee){
        super(referee.getId(),ArticleEnum.REFEREE, referee.getUserId(),
            referee.getTitle(),referee.getComment(),referee.getCreatedAt());
    }
}
