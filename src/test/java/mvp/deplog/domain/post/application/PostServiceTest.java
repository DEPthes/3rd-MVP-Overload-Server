package mvp.deplog.domain.post.application;

import mvp.deplog.domain.member.domain.Member;
import mvp.deplog.domain.member.domain.Part;
import mvp.deplog.domain.member.domain.repository.MemberRepository;
import mvp.deplog.domain.post.domain.Post;
import mvp.deplog.domain.post.domain.Stage;
import mvp.deplog.domain.post.domain.repository.PostRepository;
import mvp.deplog.domain.post.dto.request.CreatePostReq;
import mvp.deplog.domain.post.dto.response.CreatePostRes;
import mvp.deplog.domain.tag.domain.Tag;
import mvp.deplog.domain.tag.domain.repository.TagRepository;
import mvp.deplog.domain.tagging.Tagging;
import mvp.deplog.domain.tagging.repository.TaggingRepository;
import mvp.deplog.global.common.SuccessResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TaggingRepository taggingRepository;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("게시글 생성 성공 - 새 태그 포함")
    void createPost_Success_NewTags() {
        // given
        Member member = Member.builder()
                .email("test@example.com")
                .password("password")
                .name("Test User")
                .generation(1)
                .part(Part.SERVER)
                .build();

        CreatePostReq createPostReq = CreatePostReq.builder()
                .title("Test Title")
                .content("Test Content with preview and image")
                .tagNameList(List.of("Spring", "Java"))
                .build();

        Post post = Post.builder()
                .member(member)
                .title(createPostReq.getTitle())
                .content(createPostReq.getContent())
                .previewContent("Test Content")
                .searchContent("Test")
                .previewImage("/path/to/image.png")
                .stage(Stage.PUBLISHED)
                .build();

        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(tagRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(tagRepository.save(any(Tag.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        SuccessResponse<CreatePostRes> response = postService.createPost(member, createPostReq);

        // then
//        assertThat(response.getData().getPostId()).isNotNull();
        verify(postRepository, times(1)).save(any(Post.class));
        verify(tagRepository, times(2)).save(any(Tag.class));
        verify(taggingRepository, times(2)).save(any(Tagging.class));
    }

}