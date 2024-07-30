package mvp.deplog.domain.post.dto;

import lombok.Data;
import mvp.deplog.domain.block.dto.BlockReq;

import java.util.List;

@Data
public class PostReq {

    private String title;

    private List<BlockReq> blocks;

    private List<String> tags;
}
