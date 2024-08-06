package mvp.deplog.domain.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import mvp.deplog.domain.member.domain.Part;

@Data
public class PostListReq {

    @Schema(type = "ENUM(Part)", example = "SERVER", description= "목록 조회를 위한 파트입니다. 전체 목록 조회 시 null 값을 요청합니다.",
            allowableValues = {"null", "PLAN", "DESIGN", "ANDROID", "WEB", "SERVER"})
    private Part part;
}
