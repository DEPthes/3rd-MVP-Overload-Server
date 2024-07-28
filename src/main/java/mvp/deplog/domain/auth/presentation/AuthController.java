package mvp.deplog.domain.auth.presentation;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.auth.application.AuthServiceImpl;
import mvp.deplog.domain.auth.dto.LoginReq;
import mvp.deplog.domain.auth.dto.JoinReq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth API", description = "Authorization 관련 API입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController implements AuthApi{

    private final AuthServiceImpl authServiceImpl;

    @Override
    @PostMapping(value = "/join")
    public ResponseEntity<?> join(@Valid @RequestBody JoinReq joinReq) {
        return authServiceImpl.join(joinReq);
    }

    @Override
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginReq loginReq) {
        return authServiceImpl.login(loginReq);
    }
}
