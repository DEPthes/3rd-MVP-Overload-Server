package mvp.deplog.domain.comment.presentation;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.comment.application.CommentService;
import mvp.deplog.domain.comment.dto.CommentReq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentReq commentReq) {
        commentService.createComment(commentReq);
        return ResponseEntity.ok().build();
    }
}
