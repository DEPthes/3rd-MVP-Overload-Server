package mvp.deplog.domain.comment.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentRes {

    // 게시글 상세 내용 불러올 때 사용하도록 추후에 클래스 명 수정
    @Schema(type = "Long", example = "1", description = "댓글 아이디를 반환합니다.")
    private Long commentId;
}
