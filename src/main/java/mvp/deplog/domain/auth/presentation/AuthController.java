package mvp.deplog.domain.auth.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.auth.application.AuthServiceImpl;
import mvp.deplog.domain.auth.dto.SignInReq;
import mvp.deplog.domain.auth.dto.SignUpReq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth API", description = "Authorization 관련 API입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceImpl authServiceImpl;

    @Operation(summary = "회원가입", description = "회원가입을 수행합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "회원가입 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))}),
//            @ApiResponse(responseCode = "400", description = "회원가입 실패", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
//    })
    @PostMapping(value = "/sign-up")
    public ResponseEntity<?> signUp(
            @Parameter(description = "Schemas의 SignUpRequest를 참고해주세요.", required = true) @Valid @RequestBody SignUpReq signUpReq
    ) {
        return authServiceImpl.signUp(signUpReq);
    }

    @Operation(summary = "로그인", description = "로그인을 수행합니다.")
    @PostMapping(value = "/sign-in")
    public ResponseEntity<?> signIn(
            @Parameter(description = "Schemas의 SignUpRequest를 참고해주세요.", required = true) @Valid @RequestBody SignInReq signInReq
    ) {
        return authServiceImpl.signIn(signInReq);
    }
}
