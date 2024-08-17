package mvp.deplog.infrastructure.mail.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailVerifyRes {

    @Schema(type = "boolean", example = "true", description = "이메일 인증 여부를 출력합니다. 메일로 받은 링크 클릭 후라면 true 입니다.")
    private boolean verified;
}
