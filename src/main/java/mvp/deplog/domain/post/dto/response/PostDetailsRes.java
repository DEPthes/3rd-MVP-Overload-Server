package mvp.deplog.domain.post.dto.response;

import lombok.Builder;
import lombok.Data;
import mvp.deplog.domain.member.domain.Part;

import java.util.List;

@Data
@Builder
public class PostDetailsRes {
    private Long postId;
    private Boolean mine;
    private String title;
    private String content;
    private List<String> tagList;
    private Integer viewCount;
    private Integer likeCount;
    private Boolean liked;
    private Integer ScrapCount;
    private Boolean Scraped;
    private String avatarImage;
    private String author;
    private Integer generation;
    private Part part;
}
