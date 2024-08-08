package mvp.deplog.infrastructure.s3.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileUrlRes {

    @Schema(
            type = "String",
            example = "https://deplog-image-bucket.s3.amazonaws.com/post/1e4b6d5b-379b-4c9f-bdb3-2b610837e5db.png",
            description= "파일 경로입니다."
    )
    private String fileUrl;
}
