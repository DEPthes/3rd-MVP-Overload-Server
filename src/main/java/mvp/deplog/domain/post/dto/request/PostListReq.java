package mvp.deplog.domain.post.dto.request;

import lombok.Data;
import mvp.deplog.domain.member.domain.Part;

@Data
public class PostListReq {

    private Part part;
}
