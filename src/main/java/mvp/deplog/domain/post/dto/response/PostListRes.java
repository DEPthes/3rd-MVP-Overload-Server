package mvp.deplog.domain.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostListRes {

    @Schema(type = "String", example = "게시글 제목", description= "게시글 제목입니다.")
    private String title;

    @Schema(type = "String",
            example = "https://velog.velcdn.com/images/huni_huns/post/b5ae8df2-418c-4a25-b443-6a15528588-b/image/png",
            description= "게시글 첫번째 이미지의 src입니다.")
    private String previewImage;

    @Schema(type = "String", example = "**게시글 미리보기 내용**", description= "게시글 미리보기 내용입니다.")
    private String previewContent;

    @Schema(type = "LocalDateTime", example = "2024-08-05-T11:03:57.47865", description= "게시글 작성 날짜 및 시간입니다.")
    private LocalDateTime createdDate;

    @Schema(type = "String", example = "홍길동", description= "게시글 작성자의 이름입니다.")
    private String name;

    @Schema(type = "Integer", example = "0", description= "게시글 조회수입니다. 기본 값은 0입니다.")
    private Integer viewCount;

    @Schema(type = "Integer", example = "0", description= "게시글 좋아요 수입니다. 기본 값은 0입니다.")
    private Integer likeCount;

    @Schema(type = "Integer", example = "0", description= "게시글 스크랩 수입니다. 기본 값은 0입니다.")
    private Integer scrapCount;
}
