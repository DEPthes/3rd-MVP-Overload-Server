package mvp.deplog.domain.post.dto.response;

import lombok.Builder;
import lombok.Data;
import mvp.deplog.domain.comment.dto.response.CommentListRes;
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
    private Integer scrapCount;
    private Boolean scraped;

    private WriterInfo writerInfo;

    private List<CommentListRes> commentList;

    @Data
    @Builder
    public static class WriterInfo {
        private String avatarImage;
        private String name;
        private Integer generation;
        private Part part;
    }
}
