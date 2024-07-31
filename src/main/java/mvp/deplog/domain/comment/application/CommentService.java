package mvp.deplog.domain.comment.application;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.comment.domain.repository.CommentRepository;
import mvp.deplog.domain.post.domain.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


}
