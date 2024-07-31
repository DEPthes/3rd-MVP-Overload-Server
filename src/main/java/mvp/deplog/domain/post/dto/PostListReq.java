package mvp.deplog.domain.post.dto;

import lombok.Data;

@Data
public class PostListReq {
    private String part;
    private Integer page;
    private Integer size;
}
