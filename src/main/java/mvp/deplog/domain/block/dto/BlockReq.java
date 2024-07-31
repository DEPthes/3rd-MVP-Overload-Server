package mvp.deplog.domain.block.dto;

import lombok.Data;

@Data
public class BlockReq {
    private String content;

    private int sequence;

    private boolean isImage;
}
