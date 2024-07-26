package mvp.deplog.domain.auth.application;

import mvp.deplog.domain.auth.dto.SignUpReq;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    public ResponseEntity<?> signUp(SignUpReq signUpReq);
}
