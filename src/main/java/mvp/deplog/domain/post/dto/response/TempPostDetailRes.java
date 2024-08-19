package mvp.deplog.domain.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TempPostDetailRes {

    @Schema(type = "Long", example = "1", description = "게시글 아이디입니다.")
    private Long postId;

    @Schema(type = "String", example = "게시글 제목", description = "게시글 제목입니다.")
    private String title;

    @Schema(type = "Sting", example = "**게시글 상세 내용**", description = "게시글 내용입니다. 마크다운 형식으로 반환됩니다.")
    private String content;

    @Schema(type = "List<String>", example = "[Spring, Java]", description = "태그 이름 리스트입니다.")
    private List<String> tagNameList;

}
