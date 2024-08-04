package mvp.deplog.domain.post.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostListRes {

    private String title;   // 제목
    private String previewImage;   // 첫번째 이미지
    private String previewContent; // 미리보
    private LocalDateTime createdDate;  // 작성 날짜
    private String name;    // 작성자명
    private Integer viewCount;  // 조회수
    private Integer likeCount;  // 좋아요 수
    private Integer scrapCount; // 스크랩 수
}
