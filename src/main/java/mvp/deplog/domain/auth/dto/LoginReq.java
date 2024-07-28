package mvp.deplog.domain.auth.dto;

import lombok.Data;

@Data
public class LoginReq {

    private String email;

    private String password;
}
