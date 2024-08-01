package mvp.deplog.domain.comment.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCommentRes {
    private Long commentId;
}
