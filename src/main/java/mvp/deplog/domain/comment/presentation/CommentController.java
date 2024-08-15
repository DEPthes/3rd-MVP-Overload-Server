package mvp.deplog.domain.comment.presentation;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.comment.application.CommentService;
import mvp.deplog.domain.comment.application.CreateCommentService;
import mvp.deplog.domain.comment.application.CreateCommentServiceFactory;
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
    private final CreateCommentServiceFactory createCommentServiceFactory;

    @Override
    @PostMapping
    public ResponseEntity<SuccessResponse<Message>> createComment(@RequestBody CreateCommentReq createCommentReq) {
        CreateCommentService createCommentService = createCommentServiceFactory.find(createCommentReq.getParentCommentId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createCommentService.createComment(createCommentReq));
    }

    @Override
    @GetMapping("/posts/{postId}")
    public ResponseEntity<SuccessResponse<List<CommentListRes>>> getComments(@PathVariable(value = "postId") Long postId){
        return ResponseEntity.ok(commentService.getCommentList(postId));
    };
}
