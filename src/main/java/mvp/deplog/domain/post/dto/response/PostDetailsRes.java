package mvp.deplog.domain.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import mvp.deplog.domain.member.domain.Part;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class PostDetailsRes {

    @Schema(type = "Long", example = "1", description = "게시글 아이디입니다.")
    private Long postId;

    @Schema(type = "Boolean", example = "true", description = "본인 게시글이면 true, 아니면 false를 반환합니다.")
    private Boolean mine;

    @Schema(type = "String", example = "게시글 제목", description = "게시글 제목입니다.")
    private String title;

    @Schema(type = "LocalDate", example = "2024-08-11", description = "게시글 작성 날짜입니다.")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate createdDate;

    @Schema(type = "Sting", example = "**게시글 상세 내용**", description = "게시글 내용입니다. 마크다운 형식으로 반환됩니다.")
    private String content;

    @Schema(type = "List<String>", example = "[Spring, Java]", description = "태그 이름 리스트입니다.")
    private List<String> tagNameList;

    @Schema(type = "Integer", example = "0", description = "게시글 조회수입니다.")
    private Integer viewCount;

    @Schema(type = "Integer", example = "0", description = "게시글 좋아요 수입니다.")
    private Integer likeCount;

    @Schema(type = "Boolean", example = "true", description = "좋아요를 눌렀다면 true, 아니면 false를 반환합니다.")
    private Boolean liked;

    @Schema(type = "Integer", example = "0", description = "게시글 스크랩 수입니다.")
    private Integer scrapCount;

    @Schema(type = "Boolean", example = "true", description = "스크랩을 했다면 true, 아니면 false를 반환합니다.")
    private Boolean scraped;

    @Schema(type = "WriterInfo", description = "게시글 작성자 처리에 필요한 정보")
    private WriterInfo writerInfo;

    @Data
    @Builder
    public static class WriterInfo {

        @Schema(type = "String", example = "123.png", description = "작성자의 아바타 이미지입니다.")
        private String avatarImage;

        @Schema(type = "String", example = "홍길동", description = "작성자의 이름입니다.")
        private String name;

        @Schema(type = "Integer", example = "3", description = "작성자의 기수입니다.")
        private Integer generation;

        @Schema(type = "Enum(Part)", example = "SERVER", description = "작성자의 파트입니다.", allowableValues = {"PLAN", "DESIGN", "ANDROID", "WEB", "SERVER"})
        private Part part;
    }
}
