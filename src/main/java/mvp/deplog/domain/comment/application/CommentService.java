package mvp.deplog.domain.comment.application;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.comment.domain.Comment;
import mvp.deplog.domain.comment.domain.repository.CommentRepository;
import mvp.deplog.domain.comment.dto.request.CreateCommentReq;
import mvp.deplog.domain.comment.dto.response.CommentListRes;
import mvp.deplog.domain.post.domain.Post;
import mvp.deplog.domain.post.domain.repository.PostRepository;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
        }
        else{
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

        // parentId 별로 그룹핑
        Map<Long, List<CommentListRes>> commentMapByParentId = commentList.stream()
                .map(comment -> CommentListRes.builder()
                        .commentId(comment.getId())
                        .avatarImage(comment.getPost().getMember().getAvatarImage())
                        .nickname(comment.getNickname())
                        .createdDate(comment.getCreatedDate().toLocalDate())
                        .content(comment.getContent())
                        .parentCommentId(Optional.ofNullable(comment.getParentComment()).map(Comment::getId).orElse(0L))
                        .build())
                .collect(Collectors.groupingBy(CommentListRes::getParentCommentId));

        // 최상위 댓글을 필터링 해 대댓글 리스트 설정
        List<CommentListRes> commentDetails = commentMapByParentId.getOrDefault(0L, List.of()).stream()
                .peek(commentListRes -> commentListRes.setReplyList(Optional.ofNullable(commentMapByParentId.get(commentListRes.getCommentId()))
                        .orElse(List.of())))
                .collect(Collectors.toList());

        return SuccessResponse.of(commentDetails);
    }
}
