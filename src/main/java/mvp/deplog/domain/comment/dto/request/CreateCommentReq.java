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

    @Schema(type = "String", example = "www.avatarImage.png", description = "댓글 아바타 이미지 Url입니다. 비회원 혹은 닉네임 사용 미체크 시 null입니다.")
    private String avatarImage;
}
