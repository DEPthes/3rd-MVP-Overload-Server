package mvp.deplog.domain.comment.application;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.comment.domain.Comment;
import mvp.deplog.domain.comment.domain.repository.CommentRepository;
import mvp.deplog.domain.comment.dto.request.CommentReq;
import mvp.deplog.domain.post.domain.Post;
import mvp.deplog.domain.post.domain.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public void createComment(CommentReq commentReq) {
        Post post = postRepository.findById(commentReq.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음 예외처리"));

        if(commentReq.getParentCommentId() == null){
            Comment comment = Comment.commentBuilder()
                    .post(post)
                    .content(commentReq.getContent())
                    .nickname(commentReq.getNickname())
                    .build();
            commentRepository.save(comment);
        }
        else{
            Comment parentComment = commentRepository.findById(commentReq.getParentCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("허용되지 않은 부모 댓글 예외처리"));
            Comment comment = Comment.replyBuilder()
                    .post(post)
                    .parentComment(parentComment)
                    .content(commentReq.getContent())
                    .nickname(commentReq.getNickname())
                    .build();
            commentRepository.save(comment);
        }
    }
}
