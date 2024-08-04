package mvp.deplog.domain.post.dto.request;

import lombok.Data;

@Data
public class PostListReq {
    private String part;
    private Integer page;
    private Integer size;
}
