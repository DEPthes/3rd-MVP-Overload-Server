package mvp.deplog.domain.post.domain.repository;

import mvp.deplog.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
