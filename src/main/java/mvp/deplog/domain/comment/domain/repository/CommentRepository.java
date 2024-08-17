package mvp.deplog.domain.comment.domain.repository;

import mvp.deplog.domain.comment.domain.Comment;
import mvp.deplog.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 댓글 목록 조히
    List<Comment> findByPostAndParentCommentIsNull(Post post);

    // 대댓글 목록 조회
    List<Comment> findByPostAndParentCommentIsNotNull(Post post);

    void deleteByPost(Post post);
}
