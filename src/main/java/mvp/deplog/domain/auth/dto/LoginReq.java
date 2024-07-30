package mvp.deplog.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginReq {

    @Schema(type = "String", example = "deplog@naver.com", description= "계정 이메일입니다.")
    @NotBlank
    @Email
    private String email;

    @Schema(type = "String", example = "password", description= "비밀번호입니다.")
    @NotBlank
    private String password;
}
