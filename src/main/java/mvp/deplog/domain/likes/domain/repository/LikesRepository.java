package mvp.deplog.domain.likes.domain.repository;

import mvp.deplog.domain.likes.domain.Likes;
import mvp.deplog.domain.member.domain.Member;
import mvp.deplog.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    boolean existsByMemberAndPost(Member member, Post post);

    Optional<Likes> findByMemberAndPost(Member member, Post post);

    List<Likes> findAllByMember(Member member);

    void deleteByPost(Post post);

    List<Likes> findByPost(Post post);
}
