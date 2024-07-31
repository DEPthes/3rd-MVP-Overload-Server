package mvp.deplog.domain.tagging.repository;

import mvp.deplog.domain.tagging.Tagging;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaggingRepository extends JpaRepository<Tagging, Long> {
}
