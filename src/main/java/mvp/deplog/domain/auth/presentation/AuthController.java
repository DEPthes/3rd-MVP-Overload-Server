package mvp.deplog.domain.auth.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.auth.application.AuthServiceImpl;
import mvp.deplog.domain.auth.dto.LoginReq;
import mvp.deplog.domain.auth.dto.JoinReq;
import mvp.deplog.domain.auth.dto.LoginRes;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController implements AuthApi{

    private final AuthServiceImpl authServiceImpl;

    @Override
    @PostMapping(value = "/join")
    public ResponseEntity<SuccessResponse<Message>> join(@Valid @RequestBody JoinReq joinReq) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authServiceImpl.join(joinReq));
    }

    @Override
    @PostMapping(value = "/login")
    public ResponseEntity<SuccessResponse<LoginRes>> login(@Valid @RequestBody LoginReq loginReq) {
        return ResponseEntity.ok(authServiceImpl.login(loginReq));
    }
}
