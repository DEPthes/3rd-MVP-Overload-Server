package mvp.deplog.domain.auth.application;

import mvp.deplog.domain.auth.dto.LoginReq;
import mvp.deplog.domain.auth.dto.JoinReq;
import mvp.deplog.domain.auth.dto.LoginRes;
import mvp.deplog.domain.common.dto.Message;
import mvp.deplog.domain.common.dto.SuccessResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    SuccessResponse<Message> join(JoinReq joinReq);

    SuccessResponse<LoginRes> login(LoginReq loginReq);
}
