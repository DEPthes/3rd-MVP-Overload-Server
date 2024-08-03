package mvp.deplog.domain.post.application;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.member.domain.Member;
import mvp.deplog.domain.post.domain.Post;
import mvp.deplog.domain.post.domain.Stage;
import mvp.deplog.domain.post.domain.repository.PostRepository;
import mvp.deplog.domain.post.dto.response.CreatePostRes;
import mvp.deplog.domain.post.dto.request.PostReq;
import mvp.deplog.domain.tag.domain.Tag;
import mvp.deplog.domain.tag.domain.repository.TagRepository;
import mvp.deplog.domain.tagging.Tagging;
import mvp.deplog.domain.tagging.repository.TaggingRepository;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.infrastructure.markdown.MarkdownUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final TaggingRepository taggingRepository;

    @Transactional
    public SuccessResponse<CreatePostRes> createPost(Member member, PostReq postReq) {

        String content = postReq.getContent();
        String previewContent = MarkdownUtil.extractPreviewContent(content);
        String previewImage = MarkdownUtil.extractPreviewImage(content);

        Post post = Post.builder()
                .member(member)
                .title(postReq.getTitle())
                .content(content)
                .previewContent(previewContent)
                .previewImage(previewImage)
                .stage(Stage.PUBLISHED)
                .build();
        // 게시글 저장
        Post savePost = postRepository.save(post);

        // 태그 저장 & Tagging 엔티티 연결
        for(String tagName : postReq.getTagNameList()) {
            Tag tag = tagRepository.findByName(tagName)
                    .orElseGet(() ->
                            tagRepository.save(Tag.builder().name(tagName).build()));

            Tagging tagging = Tagging.builder()
                    .post(post)
                    .tag(tag)
                    .build();
            taggingRepository.save(tagging);
        }

        CreatePostRes createPostRes = CreatePostRes.builder()
                .postId(savePost.getId())
                .build();

        return SuccessResponse.of(createPostRes);
    }
}
