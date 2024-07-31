package mvp.deplog.domain.post.presentation;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.member.domain.Part;
import mvp.deplog.domain.post.application.PostService;
import mvp.deplog.domain.post.dto.PostListRes;
import mvp.deplog.domain.post.dto.PostReq;
import mvp.deplog.global.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostReq postReq, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.createPost(postReq, userDetails.getMember());
    }

    @GetMapping
    public ResponseEntity<Page<PostListRes>> getAllPost(
            @RequestParam(required = false) Part part,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<PostListRes> postList = postService.getPostByPart(part, page, size);
        return ResponseEntity.ok(postList);
    }
}
