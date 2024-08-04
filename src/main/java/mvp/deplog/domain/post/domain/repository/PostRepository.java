package mvp.deplog.domain.post.domain.repository;

import mvp.deplog.domain.member.domain.Part;
import mvp.deplog.domain.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByMemberPart(Part part, Pageable pageable);
}
