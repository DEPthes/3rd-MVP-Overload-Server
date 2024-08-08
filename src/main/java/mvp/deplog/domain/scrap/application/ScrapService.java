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
    private final MemberRepository memberRepository;

    @Transactional
    public SuccessResponse<Message> scrapPost(Long memberId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 게시글이 없습니다: " + postId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 회원이 없습니다: " + memberId));
        if(scrapRepository.existsByMemberAndPost(member, post)){
            throw new IllegalArgumentException("이미 스크랩된 게시글입니다.");
        }

        Scrap scrap = Scrap.builder()
                .post(post)
                .member(member)
                .build();

        post.incrementScrapCount();
        scrapRepository.save(scrap);    // 스크랩 저장

        Message message = Message.builder()
                .message("게시글 스크랩을 완료했습니다.")
                .build();

        return SuccessResponse.of(message);
    }
}
