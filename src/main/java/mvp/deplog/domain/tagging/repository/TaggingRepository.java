package mvp.deplog.domain.tagging.repository;

import mvp.deplog.domain.post.domain.Post;
import mvp.deplog.domain.tagging.Tagging;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaggingRepository extends JpaRepository<Tagging, Long> {

    List<Tagging> findByPost(Post post);
}
