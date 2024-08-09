package mvp.deplog.domain.comment.application;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.comment.domain.Comment;
import mvp.deplog.domain.comment.domain.repository.CommentRepository;
import mvp.deplog.domain.comment.dto.request.CreateCommentReq;
import mvp.deplog.domain.comment.dto.response.CommentListRes;
import mvp.deplog.domain.comment.dto.response.ReplyListRes;
import mvp.deplog.domain.post.domain.Post;
import mvp.deplog.domain.post.domain.repository.PostRepository;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public SuccessResponse<Message> createComment(CreateCommentReq createCommentReq) {
        Long postId = createCommentReq.getPostId();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 게시글을 찾을 수 없습니다: " + postId));

        Comment comment;
        if(createCommentReq.getParentCommentId() == null){
            comment = Comment.commentBuilder()
                    .post(post)
                    .content(createCommentReq.getContent())
                    .nickname(createCommentReq.getNickname())
                    .build();
        } else{
            Long parentCommentId = createCommentReq.getParentCommentId();
            Comment parentComment = commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 부모 댓글을 찾을 수 없습니다: " + parentCommentId));
            comment = Comment.replyBuilder()
                    .post(post)
                    .parentComment(parentComment)
                    .content(createCommentReq.getContent())
                    .nickname(createCommentReq.getNickname())
                    .build();
        }

        // 댓글 저장
        commentRepository.save(comment);

        Message message = Message.builder()
                .message("댓글 작성이 완료되었습니다.")
                .build();

        return SuccessResponse.of(message);
    }

    public SuccessResponse<List<CommentListRes>> getCommentList(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 아이디의 게시글이 없습니다: " + postId));
        List<Comment> commentList = commentRepository.findByPost(post);

        List<CommentListRes> commentDetails = new ArrayList<>();

        for(Comment comment : commentList) {
            if(comment.getParentComment() == null){
                CommentListRes commentListRes = CommentListRes.builder()
                        .commentId(comment.getId())
//                        .avatarImage() // 아바타 이미지 url
                        .nickname(comment.getNickname())
                        .createdDate(comment.getCreatedDate().toLocalDate())
                        .content(comment.getContent())
                        .replyList(new ArrayList<>())   // 대댓글 리스트 초기화
                        .build();

                commentDetails.add(commentListRes);
            } else {
                ReplyListRes replyListRes = ReplyListRes.builder()
                        .commentId(comment.getId())
                        .parentCommentId(comment.getParentComment().getId())
//                        .avatarImage()     // 아바타 이미지 url
                        .nickname(comment.getNickname())
                        .createdDate(comment.getCreatedDate().toLocalDate())
                        .content(comment.getContent())
                        .build();

                commentDetails.stream()
                        .filter(parentComment -> parentComment.getCommentId().equals(comment.getParentComment().getId()))
                        .findFirst()
                        .ifPresent(parentComment -> parentComment.getReplyList().add(replyListRes));
            }
        }

        return SuccessResponse.of(commentDetails);
    }
}
