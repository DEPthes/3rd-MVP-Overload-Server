package mvp.deplog.domain.auth.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.auth.application.AuthService;
import mvp.deplog.domain.auth.application.AuthServiceImpl;
import mvp.deplog.domain.auth.dto.request.LoginReq;
import mvp.deplog.domain.auth.dto.request.JoinReq;
import mvp.deplog.domain.auth.dto.request.ModifyPasswordReq;
import mvp.deplog.domain.auth.dto.response.EmailDuplicateCheckRes;
import mvp.deplog.domain.auth.dto.response.LoginRes;
import mvp.deplog.domain.auth.dto.response.ReissueRes;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    @PostMapping(value = "/join")
    public ResponseEntity<SuccessResponse<Message>> join(@Valid @RequestBody JoinReq joinReq) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.join(joinReq));
    }

    @Override
    @PostMapping(value = "/login")
    public ResponseEntity<SuccessResponse<LoginRes>> login(@Valid @RequestBody LoginReq loginReq) {
        return ResponseEntity.ok(authService.login(loginReq));
    }

    @Override
    @GetMapping(value = "/reissue")
    public ResponseEntity<SuccessResponse<ReissueRes>> reissue(@RequestParam(value = "refreshToken") String refreshToken) {
        return ResponseEntity.ok(authService.reissue(refreshToken));
    }

    @Override
    @GetMapping(value = "/emails")
    public ResponseEntity<SuccessResponse<EmailDuplicateCheckRes>> checkEmailDuplicate(@RequestParam(value = "email") String email) {
        return ResponseEntity.ok(authService.checkEmailDuplicate(email));
    }

    @Override
    @PutMapping("/password")
    public ResponseEntity<SuccessResponse<Message>> modifyPassword(@Valid @RequestBody ModifyPasswordReq modifyPasswordReq) {
        return ResponseEntity.ok(authService.modifyPassword(modifyPasswordReq));
    }
}
