package mvp.deplog.domain.scrap.domain.repository;

import mvp.deplog.domain.member.domain.Member;
import mvp.deplog.domain.post.domain.Post;
import mvp.deplog.domain.scrap.domain.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    boolean existsByMemberAndPost(Member member, Post post);
}
