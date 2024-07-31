package mvp.deplog.domain.post.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostListRes {
    private String title;   // 제목
    private String thumb;   // 첫번째 이미지
    private String preview; // 미리보기(첫번째 블록 내용)
    private LocalDateTime createdDate;  // 작성 날짜
    private String name;    // 작성자명
    private Integer viewCount;  // 조회수
    private Integer likeCount;  // 좋아요 수
    private Integer scrapCount; // 스크랩 수
}
