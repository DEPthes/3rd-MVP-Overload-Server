package mvp.deplog.domain.comment.application;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.comment.domain.Comment;
import mvp.deplog.domain.comment.domain.repository.CommentRepository;
import mvp.deplog.domain.comment.dto.request.CommentReq;
import mvp.deplog.domain.comment.dto.response.CommentRes;
import mvp.deplog.domain.post.domain.Post;
import mvp.deplog.domain.post.domain.repository.PostRepository;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public SuccessResponse<Message> createComment(CommentReq commentReq) {
        Long postId = commentReq.getPostId();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 게시글을 찾을 수 없습니다: " + postId));

        Comment comment;
        if(commentReq.getParentCommentId() == null){
            comment = Comment.commentBuilder()
                    .post(post)
                    .content(commentReq.getContent())
                    .nickname(commentReq.getNickname())
                    .build();
        }
        else{
            Long parentCommentId = commentReq.getParentCommentId();
            Comment parentComment = commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 부모 댓글을 찾을 수 없습니다: " + parentCommentId));
            comment = Comment.replyBuilder()
                    .post(post)
                    .parentComment(parentComment)
                    .content(commentReq.getContent())
                    .nickname(commentReq.getNickname())
                    .build();
        }

        // 댓글 저장
        Comment saveComment = commentRepository.save(comment);

        Message message = Message.builder()
                .message("댓글 작성이 완료되었습니다.")
                .build();

        return SuccessResponse.of(message);
    }
}
