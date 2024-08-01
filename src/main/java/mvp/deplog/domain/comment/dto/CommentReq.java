package mvp.deplog.domain.comment.dto;

import lombok.Data;

@Data
public class CommentReq {
    private Long postId;
    private Long parentCommentId;
    private String content;
    private String nickname;
}
