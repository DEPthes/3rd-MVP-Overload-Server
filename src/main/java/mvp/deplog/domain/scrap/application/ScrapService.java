package mvp.deplog.domain.scrap.application;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.member.domain.Member;
import mvp.deplog.domain.member.domain.repository.MemberRepository;
import mvp.deplog.domain.post.domain.Post;
import mvp.deplog.domain.post.domain.repository.PostRepository;
import mvp.deplog.domain.scrap.domain.Scrap;
import mvp.deplog.domain.scrap.domain.repository.ScrapRepository;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ScrapService {

    private final ScrapRepository scrapRepository;
    private final PostRepository postRepository;

    @Transactional
    public SuccessResponse<Message> scrapPost(Member member, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 게시글이 없습니다: " + postId));

        Scrap scrap = Scrap.builder()
                .post(post)
                .member(member)
                .build();

        post.incrementScrapCount();
        postRepository.save(post);      // 스크랩 수 저장
        scrapRepository.save(scrap);    // 스크랩 저장

        Message message = Message.builder()
                .message("게시글 스크랩을 완료했습니다.")
                .build();

        return SuccessResponse.of(message);
    }
}
