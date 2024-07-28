package mvp.deplog.domain.auth.dto;

import lombok.Data;
import mvp.deplog.domain.member.domain.Part;

@Data
public class JoinReq {

    private String email;

    private String password;

    private String name;

    private Part part;

    private Integer generation;
}
