package mvp.deplog.domain.comment.presentation;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.comment.application.CommentService;
import mvp.deplog.domain.comment.dto.request.CreateCommentReq;
import mvp.deplog.domain.comment.dto.response.CommentListRes;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController implements CommentApi {

    private final CommentService commentService;

    @Override
    @PostMapping
    public ResponseEntity<SuccessResponse<Message>> createComment(@RequestBody CreateCommentReq createCommentReq) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.createComment(createCommentReq));
    }

    @Override
    @GetMapping("/{postId}")
    public ResponseEntity<SuccessResponse<List<CommentListRes>>> getComments(@PathVariable(value = "postId") Long postId){
        return ResponseEntity.ok(commentService.getCommentList(postId));
    };
}
