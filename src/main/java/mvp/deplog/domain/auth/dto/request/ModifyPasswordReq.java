package mvp.deplog.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ModifyPasswordReq {

    @Schema(type = "String", example = "deplog@naver.com", description= "계정 이메일입니다.")
    @NotBlank
    @Email
    private String email;

    @Schema(type = "String", example = "password", description= "비밀번호입니다.")
    @NotBlank
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]*$")
    private String password;
}
