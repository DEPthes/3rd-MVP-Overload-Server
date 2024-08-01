package mvp.deplog.domain.comment.presentation;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.comment.application.CommentService;
import mvp.deplog.domain.comment.dto.request.CommentReq;
import mvp.deplog.domain.comment.dto.response.CreateCommentRes;
import mvp.deplog.global.common.SuccessResponse;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.stream.events.Comment;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController implements CommentApi {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<SuccessResponse<CreateCommentRes>> createComment(@RequestBody CommentReq commentReq) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.createComment(commentReq));
    }
}
