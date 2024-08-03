package mvp.deplog.domain.post.presentation;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.post.application.PostService;
import mvp.deplog.domain.post.dto.response.CreatePostRes;
import mvp.deplog.domain.post.dto.request.PostReq;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.global.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController implements PostApi {

    private final PostService postService;

    @Override
    @PostMapping
    public ResponseEntity<SuccessResponse<CreatePostRes>> createPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PostReq postReq) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postService.createPost(userDetails.getMember(), postReq));
    }
}
