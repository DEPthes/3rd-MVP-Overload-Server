package mvp.deplog.domain.comment.application;

import mvp.deplog.domain.comment.dto.request.CreateCommentReq;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;

public interface CreateCommentService {

    boolean supports(Long parentCommentId);

    SuccessResponse<Message> createComment(CreateCommentReq createCommentReq);
}
