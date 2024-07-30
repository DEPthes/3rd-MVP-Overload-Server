package mvp.deplog.domain.post.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "title") // null 가능 - 임시 저장
    private String title;

    @Column(name = "like_count")
    @Min(value = 0)
    private Integer likeCount;

    @Column(name = "scrap_count")
    @Min(value = 0)
    private Integer scrapCount;

    @Column(name = "view_count")
    @Min(value = 0)
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
