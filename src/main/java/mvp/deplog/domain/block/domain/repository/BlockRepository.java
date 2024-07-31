package mvp.deplog.domain.block.domain.repository;

import mvp.deplog.domain.block.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {
}
