package mvp.deplog.domain.post.domain.repository;

import mvp.deplog.domain.member.domain.Member;
import mvp.deplog.domain.member.domain.Part;
import mvp.deplog.domain.post.domain.Post;
import mvp.deplog.domain.post.domain.Stage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findById(Long id);

    @Query("SELECT p FROM Post p WHERE p.member.part IN :parts")
    Page<Post> findByMemberPart(@Param("parts") List<Part> partGroup, Pageable pageable);

    Page<Post> findByTitleContainingOrContentContaining(String titleSearchWord, String contentSearchWord, Pageable pageable);

    List<Post> findByMemberAndStageOrderByCreatedDateDesc(Member member, Stage stage);
}