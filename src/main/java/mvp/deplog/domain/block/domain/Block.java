package mvp.deplog.domain.block.domain;

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
@Table(name = "block")
public class Block extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "block_id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "content")
    private String content; // 내용 or 이미지 url

    @Column(name = "sequence", nullable = false)
    private int sequence;

    @Column(name = "is_Image", nullable = false)
    private boolean isImage;

    @Builder
    public Block(Post post, String content, int sequence, boolean isImage) {
        this.post = post;
        this.content = content;
        this.sequence = sequence;
        this.isImage = isImage;
    }
}
