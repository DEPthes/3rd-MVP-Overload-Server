package mvp.deplog.domain.comment.domain.repository;

import mvp.deplog.domain.comment.domain.Comment;
import mvp.deplog.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);

    List<Comment> findByParentComment(Comment parentComment);
}
