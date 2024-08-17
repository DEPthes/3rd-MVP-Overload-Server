package mvp.deplog.domain.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateCommentReq {

    @Schema(type = "Long", example = "1", description = "부모 댓글 아이디입니다. 대댓글 작성 시 사용됩니다.")
    private Long parentCommentId;

    @Schema(type = "Long", example = "1", description = "게시글 아이디입니다.")
    private Long postId;

    @Schema(type = "String", example = "**댓글 내용**", description = "댓글 내용입니다.")
    private String content;

    @Schema(type = "String", example = "닉네임", description = "닉네임입니다.")
    private String nickname;

    @Schema(type = "boolean", example = "true", description = "닉네임 사용 체크박스 체크 여부입니다. 회원이 체크 안한 경우, 비회원의 경우 모두 true입니다. 회원이 누른 경우만 false입니다.")
    private boolean useNicknameChecked;
}
