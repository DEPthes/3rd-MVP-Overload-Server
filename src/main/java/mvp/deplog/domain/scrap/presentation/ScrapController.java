package mvp.deplog.domain.scrap.presentation;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.post.domain.repository.PostRepository;
import mvp.deplog.domain.post.dto.response.PostListRes;
import mvp.deplog.domain.scrap.application.ScrapService;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.global.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/scraps")
public class ScrapController implements ScrapApi {

    private final ScrapService scrapService;

    @Override
    @PostMapping("/{post_id}")
    public ResponseEntity<SuccessResponse<Message>> scrapPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                              @PathVariable(value = "post_id") Long postId){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(scrapService.scrapPost(userDetails.getMember().getId(), postId));
    }
}
