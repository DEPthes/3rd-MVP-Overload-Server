package mvp.deplog.domain.comment.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mvp.deplog.domain.common.BaseEntity;
import mvp.deplog.domain.post.domain.Post;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "Comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment parentComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "depth", nullable = false)
    private int depth;

    @Builder(builderMethodName = "commentBuilder")
    public Comment(Post post, String content, String nickname) {
        this.parentComment = null;
        this.post = post;
        this.content = content;
        this.nickname = nickname;
        this.depth = 1;
    }

    @Builder(builderMethodName = "replyBuilder")
    public Comment(Comment parentComment, Post post, String content, String nickname) {
        this.parentComment = parentComment;
        this.post = post;
        this.content = content;
        this.nickname = nickname;
        this.depth = parentComment.getDepth() + 1;
    }
}
