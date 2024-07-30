package mvp.deplog.domain.post.presentation;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.post.application.PostService;
import mvp.deplog.domain.post.dto.PostReq;
import mvp.deplog.global.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private final PostService postService;

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostReq postReq, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.createPost(postReq, userDetails.getMember());
    }
}
