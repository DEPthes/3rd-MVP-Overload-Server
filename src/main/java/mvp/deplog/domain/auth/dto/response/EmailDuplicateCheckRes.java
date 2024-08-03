package mvp.deplog.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailDuplicateCheckRes {

    @Schema(type = "boolean", example = "true", description= "닉네임 **사용 가능 여부**입니다. 값이 true면 중복 x, 사용 가능합니다.")
    private boolean availability;
}
