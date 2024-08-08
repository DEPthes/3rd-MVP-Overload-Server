package mvp.deplog.domain.likes.presentation;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.likes.application.LikesService;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.global.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/likes")
public class LikesController implements LikesApi {

    private final LikesService likesService;

    @Override
    @PostMapping("/{postId}")
    public ResponseEntity<SuccessResponse<Message>> likesPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                              @PathVariable(value = "postId") Long postId){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(likesService.likesPost(userDetails.getMember().getId(), postId));
    }

    @Override
    @DeleteMapping("/{postId}")
    public ResponseEntity<SuccessResponse<Message>> deleteLikesPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                    @PathVariable(value = "postId") Long postId){
        return ResponseEntity.ok(likesService.deleteLikesPost(userDetails.getMember().getId(), postId));
    }
}
