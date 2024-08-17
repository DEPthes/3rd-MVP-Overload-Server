package mvp.deplog.domain.comment.application;

import mvp.deplog.domain.comment.dto.request.CreateCommentReq;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.global.security.UserDetailsImpl;

public interface CreateCommentService {

    boolean supports(UserDetailsImpl userDetails, Long parentCommentId);

    SuccessResponse<Message> createComment(UserDetailsImpl userDetails, CreateCommentReq createCommentReq);
}
