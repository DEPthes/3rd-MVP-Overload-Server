package mvp.deplog.domain.scrap.presentation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.global.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Scrap API", description = "스크랩 관련 API 입니다.")
public interface ScrapApi {

    @PostMapping("/{post_id}")
    ResponseEntity<SuccessResponse<Message>> scrapPost(
            @Parameter(description = "Access Token을 입력해주세요.", required = true) @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Parameter(description = "Schemas의 ScrapReq를 참고해주세요.", required = true) @PathVariable(value = "post_id")Long postId
            );
}