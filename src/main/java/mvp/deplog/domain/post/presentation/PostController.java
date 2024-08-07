package mvp.deplog.domain.post.presentation;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.member.domain.Part;
import mvp.deplog.domain.post.application.PostService;
import mvp.deplog.domain.post.dto.response.CreatePostRes;
import mvp.deplog.domain.post.dto.request.CreatePostReq;
import mvp.deplog.global.common.PageResponse;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.global.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController implements PostApi {

    private final PostService postService;

    @Override
    @PostMapping
    public ResponseEntity<SuccessResponse<CreatePostRes>> createPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CreatePostReq createPostReq) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postService.createPost(userDetails.getMember(), createPostReq));
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<SuccessResponse<PageResponse>> getAllPost(@RequestParam(defaultValue = "1") Integer page,
        @RequestParam(defaultValue = "10") Integer size){
        return ResponseEntity.ok(postService.getAllPosts(page-1, size));
    }

    @Override
    @GetMapping("/{part}")
    public ResponseEntity<SuccessResponse<PageResponse>> getPartPost(@PathVariable("part") Part part,
        @RequestParam(defaultValue = "1") Integer page,
        @RequestParam(defaultValue = "10") Integer size){
        return ResponseEntity.ok(postService.getPosts(part, page-1, size));
    }
}
