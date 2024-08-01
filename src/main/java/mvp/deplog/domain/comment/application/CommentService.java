package mvp.deplog.domain.comment.application;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.comment.domain.Comment;
import mvp.deplog.domain.comment.domain.repository.CommentRepository;
import mvp.deplog.domain.comment.dto.request.CommentReq;
import mvp.deplog.domain.comment.dto.response.CreateCommentRes;
import mvp.deplog.domain.post.domain.Post;
import mvp.deplog.domain.post.domain.repository.PostRepository;
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
    public SuccessResponse<CreateCommentRes> createComment(CommentReq commentReq) {
        Post post = postRepository.findById(commentReq.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음 예외처리"));

        Comment comment;
        if(commentReq.getParentCommentId() == null){
            comment = Comment.commentBuilder()
                    .post(post)
                    .content(commentReq.getContent())
                    .nickname(commentReq.getNickname())
                    .build();
        }
        else{
            Comment parentComment = commentRepository.findById(commentReq.getParentCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("허용되지 않은 부모 댓글 예외처리"));
            comment = Comment.replyBuilder()
                    .post(post)
                    .parentComment(parentComment)
                    .content(commentReq.getContent())
                    .nickname(commentReq.getNickname())
                    .build();
        }


        // 댓글 저장
        Comment saveComment = commentRepository.save(comment);

        CreateCommentRes createCommentRes = CreateCommentRes.builder()
                .commentId(saveComment.getId())
                .build();

        return SuccessResponse.of(createCommentRes);
    }
}
