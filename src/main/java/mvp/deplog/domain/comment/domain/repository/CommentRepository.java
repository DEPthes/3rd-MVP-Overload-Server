package mvp.deplog.domain.comment.domain.repository;

import mvp.deplog.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
