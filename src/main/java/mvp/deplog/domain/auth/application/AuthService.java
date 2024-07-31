package mvp.deplog.domain.auth.application;

import mvp.deplog.domain.auth.dto.LoginReq;
import mvp.deplog.domain.auth.dto.JoinReq;
import mvp.deplog.domain.auth.dto.LoginRes;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;

public interface AuthService {

    SuccessResponse<Message> join(JoinReq joinReq);

    SuccessResponse<LoginRes> login(LoginReq loginReq);
}
