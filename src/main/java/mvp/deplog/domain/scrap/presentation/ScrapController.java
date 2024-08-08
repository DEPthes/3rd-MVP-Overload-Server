package mvp.deplog.domain.scrap.presentation;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.scrap.application.ScrapService;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.global.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/scraps")
public class ScrapController implements ScrapApi {

    private final ScrapService scrapService;

    @Override
    @PostMapping("/{postId}")
    public ResponseEntity<SuccessResponse<Message>> scrapPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                              @PathVariable(value = "postId") Long postId){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(scrapService.scrapPost(userDetails.getMember().getId(), postId));
    }

    @Override
    @DeleteMapping("/{postId}")
    public ResponseEntity<SuccessResponse<Message>> deleteScrapPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                    @PathVariable(value = "postId") Long postId){
        return ResponseEntity.ok(scrapService.deleteScrapPost(userDetails.getMember().getId(), postId));
    }
}
