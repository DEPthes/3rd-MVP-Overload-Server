package mvp.deplog.domain.comment.application;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.comment.domain.Comment;
import mvp.deplog.domain.comment.domain.repository.CommentRepository;
import mvp.deplog.domain.comment.dto.request.CreateCommentReq;
import mvp.deplog.domain.member.domain.Member;
import mvp.deplog.domain.member.domain.Role;
import mvp.deplog.domain.member.domain.repository.MemberRepository;
import mvp.deplog.domain.post.domain.Post;
import mvp.deplog.domain.post.domain.repository.PostRepository;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.global.security.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CreateMemberCommentServiceImpl implements CreateCommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    @Override
    public boolean supports(UserDetailsImpl userDetails, Long parentCommentId) {
        return (userDetails != null && !userDetails.getMember().getRole().equals(Role.APPLICANT)) && parentCommentId == null;
    }


    @Override
    @Transactional
    public SuccessResponse<Message> createComment(UserDetailsImpl userDetails, CreateCommentReq createCommentReq) {
        Long postId = createCommentReq.getPostId();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 게시글을 찾을 수 없습니다: " + postId));

        Member member = memberRepository.findById(userDetails.getMember().getId())
                .orElseThrow(() -> new IllegalArgumentException("해당되는 아이디의 회원이 존재하지 않습니다."));

        Comment comment = Comment.memberCommentBuilder()
                .post(post)
                .member(member)
                .content(createCommentReq.getContent())
                .nickname(createCommentReq.getNickname())
                .useNickname(createCommentReq.isUseNicknameChecked())
                .build();

        commentRepository.save(comment);

        Message message = Message.builder()
                .message("댓글 작성이 완료되었습니다.")
                .build();

        return SuccessResponse.of(message);
    }
}
