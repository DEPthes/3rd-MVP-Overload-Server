package mvp.deplog.domain.auth.dto;

import lombok.Data;

@Data
public class SignInRes {

    private String accessToken;

    private String refreshToken;
}
