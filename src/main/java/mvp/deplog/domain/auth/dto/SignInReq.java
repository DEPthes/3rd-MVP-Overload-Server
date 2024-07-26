package mvp.deplog.domain.auth.dto;

import lombok.Data;

@Data
public class SignInReq {

    private String email;

    private String password;
}
