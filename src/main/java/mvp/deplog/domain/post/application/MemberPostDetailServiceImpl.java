package mvp.deplog.domain.post.application;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.likes.domain.repository.LikesRepository;
import mvp.deplog.domain.member.domain.Member;
import mvp.deplog.domain.member.domain.Role;
import mvp.deplog.domain.member.domain.repository.MemberRepository;
import mvp.deplog.domain.post.domain.Post;
import mvp.deplog.domain.post.domain.repository.PostRepository;
import mvp.deplog.domain.post.dto.response.MemberPostDetailRes;
import mvp.deplog.domain.post.exception.ResourceNotFoundException;
import mvp.deplog.domain.scrap.domain.repository.ScrapRepository;
import mvp.deplog.domain.tagging.repository.TaggingRepository;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.global.security.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberPostDetailServiceImpl implements PostDetailService<MemberPostDetailRes> {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final TaggingRepository taggingRepository;
    private final LikesRepository likesRepository;
    private final ScrapRepository scrapRepository;

    @Override
    public boolean supports(UserDetailsImpl userDetails) {
        return userDetails.getMember().getRole().equals(Role.MEMBER) || userDetails.getMember().getRole().equals(Role.ADMIN);
    }

    @Override
    @Transactional
    public SuccessResponse<MemberPostDetailRes> getPostDetail(UserDetailsImpl userDetails, Long postId) {
        Long memberId = userDetails.getMember().getId();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 id의 게시글을 찾을 수 없습니다: " + postId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 멤버를 찾을 수 없습니다: " + memberId));

        // Tagging Entity에 Tag 목록 조회
        List<String> tagNameList = taggingRepository.findByPost(post).stream()
                .map(tagging -> tagging.getTag().getName())
                .collect(Collectors.toList());

        // 본인 게시글인지 확인
        Member writer = post.getMember();
        boolean sameUser = member.equals(writer);

        // 좋아요, 스크랩 확인
        boolean liked = likesRepository.existsByMemberAndPost(member, post);
        boolean scraped = scrapRepository.existsByMemberAndPost(member, post);

        post.incrementViewCount();  // 조회수 증가

        MemberPostDetailRes memberPostDetailRes = MemberPostDetailRes.builder()
                .postId(post.getId())
                .mine(sameUser)
                .title(post.getTitle())
                .createdDate(post.getCreatedDate().toLocalDate())
                .content(post.getContent())
                .tagNameList(tagNameList)
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .scrapCount(post.getScrapCount())
                .liked(liked)
                .scraped(scraped)
                .writerInfo(MemberPostDetailRes.WriterInfo.builder()
//                        .avatarImage(post.getMember().getAvatarImage())
                        .name(post.getMember().getName())
                        .generation(post.getMember().getGeneration())
                        .part(post.getMember().getPart())
                        .build())
                .build();

        return SuccessResponse.of(memberPostDetailRes);
    }
}
