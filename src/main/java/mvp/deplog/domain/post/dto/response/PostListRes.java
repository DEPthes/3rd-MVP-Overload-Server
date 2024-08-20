package mvp.deplog.domain.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import mvp.deplog.domain.member.dto.Avatar;

import java.time.LocalDate;

@Data
@Builder
public class PostListRes {

    @Schema(type = "Long", example = "1", description = "게시글 아이디입니다.")
    private Long id;

    @Schema(type = "String", example = "게시글 제목", description= "게시글 제목입니다.")
    private String title;

    @Schema(type = "String",
            example = "https://velog.velcdn.com/images/huni_huns/post/b5ae8df2-418c-4a25-b443-6a15528588-b/image/png",
            description= "게시글 첫번째 이미지의 src입니다.")
    private String previewImage;

    @Schema(type = "String", example = "**게시글 미리보기 내용**", description= "게시글 미리보기 내용입니다.")
    private String previewContent;

    @Schema(type = "LocalDate", example = "2024-08-05", description= "게시글 작성 날짜입니다.")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate createdDate;

    @Schema(type = "Avatar", description = "작성자의 아바타 이미지 객체입니다.")
    private Avatar avatar;

    @Schema(type = "String", example = "홍길동", description= "게시글 작성자의 이름입니다.")
    private String name;

    @Schema(type = "Integer", example = "0", description= "게시글 조회수입니다. 기본 값은 0입니다.")
    private Integer viewCount;

    @Schema(type = "Integer", example = "0", description= "게시글 좋아요 수입니다. 기본 값은 0입니다.")
    private Integer likeCount;

    @Schema(type = "Integer", example = "0", description= "게시글 스크랩 수입니다. 기본 값은 0입니다.")
    private Integer scrapCount;
}
