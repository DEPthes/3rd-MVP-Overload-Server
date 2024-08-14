package mvp.deplog.domain.scrap.application;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.member.domain.Member;
import mvp.deplog.domain.member.domain.repository.MemberRepository;
import mvp.deplog.domain.post.domain.Post;
import mvp.deplog.domain.post.domain.repository.PostRepository;
import mvp.deplog.domain.post.dto.response.PostListRes;
import mvp.deplog.domain.scrap.domain.Scrap;
import mvp.deplog.domain.scrap.domain.repository.ScrapRepository;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.PageInfo;
import mvp.deplog.global.common.PageResponse;
import mvp.deplog.global.common.SuccessResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Transactional
    public SuccessResponse<Message> deleteScrapPost(Long memberId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 게시글이 없습니다: " + postId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 회원이 없습니다: " + memberId));
        Scrap scrap = scrapRepository.findByMemberAndPost(member, post)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글은 스크랩하지 않았습니다. 먼저 스크랩해주세요."));

        post.decrementScrapCount();
        scrapRepository.delete(scrap);

        Message message = Message.builder()
                .message("게시글 스크랩 해제를 완료했습니다.")
                .build();

        return SuccessResponse.of(message);
    }

    public SuccessResponse<PageResponse> getScrapPosts(Long memberId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 멤버를 찾을 수 없습니다: " + memberId));
        Page<Scrap> scrapPosts = scrapRepository.findByMember(member, pageable);

        Page<PostListRes> scrapPostList = scrapPosts.map(scrap -> {
            Post post = scrap.getPost();
            return PostListRes.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .previewContent(post.getPreviewContent())
                    .previewImage(post.getPreviewImage())
                    .createdDate(post.getCreatedDate().toLocalDate())
                    .name(post.getMember().getName())
                    .viewCount(post.getViewCount())
                    .likeCount(post.getLikeCount())
                    .scrapCount(post.getScrapCount())
                    .build();
        });

        PageInfo pageInfo = PageInfo.toPageInfo(pageable, scrapPosts);
        PageResponse pageResponse = PageResponse.toPageResponse(pageInfo, scrapPostList.getContent());

        return SuccessResponse.of(pageResponse);
    }
}
