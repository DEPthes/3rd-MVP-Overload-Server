package mvp.deplog.domain.post.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mvp.deplog.domain.common.BaseEntity;
import mvp.deplog.domain.member.domain.Member;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "title") // null 가능 - 임시 저장
    private String title;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "scrap_count")
    private Integer scrapCount;

    @Column(name = "view_count")
    private Integer viewCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "stage", nullable = false)
    private Stage stage;

    @Builder
    public Post(Member member, String title, Stage stage) {
        this.member = member;
        this.title = title;
        this.likeCount = 0;
        this.scrapCount = 0;
        this.viewCount = 0;
        this.stage = stage;
    }
}
