package mvp.deplog.domain.auth.application;

import mvp.deplog.domain.auth.dto.LoginReq;
import mvp.deplog.domain.auth.dto.JoinReq;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> join(JoinReq joinReq);

    ResponseEntity<?> login(LoginReq loginReq);
}
