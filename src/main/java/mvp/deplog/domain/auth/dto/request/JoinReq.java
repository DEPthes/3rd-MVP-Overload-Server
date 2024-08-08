package mvp.deplog.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import mvp.deplog.domain.member.domain.Part;

@Data
public class JoinReq {

    @Schema(type = "String", example = "deplog@naver.com", description= "계정 이메일입니다.")
    @NotBlank
    @Email
    private String email;

    @Schema(type = "String", example = "password", description= "비밀번호입니다.")
    @NotBlank
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]*$")
    private String password;

    @Schema(type = "String", example = "홍길동", description= "회원의 이름입니다.")
    @NotBlank
    @Size(min = 2, max = 20)
    @Pattern(regexp = "^[a-zA-Z가-힣]+$")
    private String name;

    @Schema(type = "ENUM(Part)", example = "SERVER", description= "회원의 파트입니다.", allowableValues = {"PLAN", "DESIGN", "ANDROID", "WEB", "SERVER"})
    private Part part;

    @Schema(type = "int", example = "2", description= "회원의 기수입니다.")
    private int generation;
}
