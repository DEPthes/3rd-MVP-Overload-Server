package mvp.deplog.domain.likes.domain.repository;

import mvp.deplog.domain.likes.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
}
