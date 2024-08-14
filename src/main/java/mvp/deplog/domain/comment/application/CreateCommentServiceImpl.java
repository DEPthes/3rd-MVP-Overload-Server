package mvp.deplog.domain.comment.application;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.comment.domain.Comment;
import mvp.deplog.domain.comment.domain.repository.CommentRepository;
import mvp.deplog.domain.comment.dto.request.CreateCommentReq;
import mvp.deplog.domain.post.domain.Post;
import mvp.deplog.domain.post.domain.repository.PostRepository;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CreateCommentServiceImpl implements CreateCommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    public boolean supports(Long parentCommentId) {
        return parentCommentId == null;
    }

    @Override
    @Transactional
    public SuccessResponse<Message> createComment(CreateCommentReq createCommentReq) {
        Long postId = createCommentReq.getPostId();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 게시글을 찾을 수 없습니다: " + postId));

        Comment comment = Comment.commentBuilder()
                .post(post)
                .content(createCommentReq.getContent())
                .nickname(createCommentReq.getNickname())
                .avatarImage(createCommentReq.getAvatarImage())
                .build();

        commentRepository.save(comment);

        Message message = Message.builder()
                .message("댓글 작성이 완료되었습니다.")
                .build();

        return SuccessResponse.of(message);
    }
}
