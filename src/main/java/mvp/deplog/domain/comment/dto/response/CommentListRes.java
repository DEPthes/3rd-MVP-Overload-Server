package mvp.deplog.domain.comment.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CommentListRes {

    @Schema(type = "Long", example = "1", description = "댓글 아이디를 반환합니다.")
    private Long commentId;

    @Schema(type = "String", example = "avatarImage.png", description = "댓글 작성자의 아바타이미지를 반환합니다.")
    private String avatarImage;

    @Schema(type = "String", example = "댓글 작성자", description = "댓글 작성자의 닉네임을 반환합니다.")
    private String nickname;

    @Schema(type = "String", example = "**댓글 내용**", description = "댓글 내용을 반환합니다.")
    private String content;

    @Schema(type = "localDate", example = "2024-08-01", description = "댓글 작성 날짜를 반환합니다.")
    private LocalDate createdDate;
}
