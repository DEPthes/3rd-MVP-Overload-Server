package mvp.deplog.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class PostReq {

    @Schema(type = "String", example = "게시글 제목", description= "게시글 제목입니다.")
    private String title;

    @Schema(type = "String", example = "**게시글 내용**", description= "게시글 내용입니다. 마크다운 형식으로 저장됩니다.")
    private String content;

    @Schema(type = "List<String>", example = "[Spring, React, JS]", description= "태그 이름 리스트입니다.")
    private List<String> tagNameList;
}
