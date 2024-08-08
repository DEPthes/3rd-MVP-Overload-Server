package mvp.deplog.domain.likes.application;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.likes.domain.Likes;
import mvp.deplog.domain.likes.domain.repository.LikesRepository;
import mvp.deplog.domain.member.domain.Member;
import mvp.deplog.domain.member.domain.repository.MemberRepository;
import mvp.deplog.domain.post.domain.Post;
import mvp.deplog.domain.post.domain.repository.PostRepository;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LikesService {

    private final LikesRepository likesRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public SuccessResponse<Message> likesPost(Long memberId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 게시글이 없습니다: " + postId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 회원이 없습니다: " + memberId));
        if(likesRepository.existsByMemberAndPost(member, post)){
            throw new IllegalArgumentException("이미 좋아요 설정한 게시글입니다.");
        }

        Likes likes = Likes.builder()
                .post(post)
                .member(member)
                .build();

        post.incrementLikesCount();
        likesRepository.save(likes);

        Message message = Message.builder()
                .message("게시글 좋아요를 완료했습니다.")
                .build();

        return SuccessResponse.of(message);
    }

    @Transactional
    public SuccessResponse<Message> deleteLikesPost(Long memberId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 게시글이 없습니다: " + postId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 회원이 없습니다: " + memberId));
        Likes likes = likesRepository.findByMemberAndPost(member, post)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글에 좋아요 설정을 하지 않았습니다. 먼저 좋아요를 설정을 해주세요."));

        post.decrementLikesCount();
        likesRepository.delete(likes);

        Message message = Message.builder()
                .message("게시글 좋아요 해제를 완료했습니다.")
                .build();

        return SuccessResponse.of(message);
    }
}
