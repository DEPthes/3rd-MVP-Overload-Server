package mvp.deplog.domain.comment.presentation;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.comment.application.CommentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;


}
