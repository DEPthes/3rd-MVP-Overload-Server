package mvp.deplog.domain.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRes {

    private String accessToken;

    private String refreshToken;
}
