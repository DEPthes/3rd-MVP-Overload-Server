package mvp.deplog.domain.post.dto.response;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@Builder
public class PostSearchRes {

    @Schema(type = "String", example = "검색어", description = "게시글 검색어 입니다.")
    private String searchWord;

    @ArraySchema(schema = @Schema(implementation = PostListRes.class))
    @Schema(description = "게시글 리스트")
    private Page<PostListRes> postList;
}
