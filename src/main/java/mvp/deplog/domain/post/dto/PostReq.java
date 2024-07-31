package mvp.deplog.domain.post.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostReq {

    private String title;

    private String content;

    private List<String> tagNameList;
}
