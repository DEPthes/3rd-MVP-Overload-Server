package mvp.deplog.domain.auth.dto;

import lombok.Data;
import mvp.deplog.domain.user.domain.Part;

@Data
public class JoinReq {

    private String email;

    private String password;

    private String username;

    private Part part;

    private Integer generation;
}
