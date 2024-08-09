package mvp.deplog.domain.post.application;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.comment.domain.repository.CommentRepository;
import mvp.deplog.domain.likes.domain.repository.LikesRepository;
import mvp.deplog.domain.member.domain.Member;
import mvp.deplog.domain.member.domain.Part;
import mvp.deplog.domain.member.domain.repository.MemberRepository;
import mvp.deplog.domain.post.domain.Post;
import mvp.deplog.domain.post.domain.Stage;
import mvp.deplog.domain.post.domain.repository.PostRepository;
import mvp.deplog.domain.post.dto.response.CreatePostRes;
import mvp.deplog.domain.post.dto.request.CreatePostReq;
import mvp.deplog.domain.post.dto.response.PostDetailsRes;
import mvp.deplog.domain.post.dto.response.PostListRes;
import mvp.deplog.domain.post.exception.ResourceNotFoundException;
import mvp.deplog.domain.scrap.domain.repository.ScrapRepository;
import mvp.deplog.domain.tag.domain.Tag;
import mvp.deplog.domain.tag.domain.repository.TagRepository;
import mvp.deplog.domain.tagging.Tagging;
import mvp.deplog.domain.tagging.repository.TaggingRepository;
import mvp.deplog.global.common.PageInfo;
import mvp.deplog.global.common.PageResponse;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.infrastructure.markdown.MarkdownUtil;
import mvp.deplog.infrastructure.s3.application.FileService;
import mvp.deplog.infrastructure.s3.dto.response.FileUrlRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {

    private final FileService fileService;

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final TaggingRepository taggingRepository;
    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;
    private final ScrapRepository scrapRepository;

    private static final String DIRNAME = "post";
    private final MemberRepository memberRepository;

    @Transactional
    public SuccessResponse<CreatePostRes> createPost(Member member, CreatePostReq createPostReq) {

        String content = createPostReq.getContent();
        String previewContent = MarkdownUtil.extractPreviewContent(content);
        String previewImage = MarkdownUtil.extractPreviewImage(content);

        Post post = Post.builder()
                .member(member)
                .title(createPostReq.getTitle())
                .content(content)
                .previewContent(previewContent)
                .previewImage(previewImage)
                .stage(Stage.PUBLISHED)
                .build();
        // 게시글 저장
        Post savePost = postRepository.save(post);

        // 태그 저장 & Tagging 엔티티 연결
        for(String tagName : createPostReq.getTagNameList()) {
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

    public SuccessResponse<PageResponse> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Post> posts = postRepository.findAll(pageable);

        Page<PostListRes> postList = posts.map(post -> PostListRes.builder()
                .id(post.getId())
                .title(post.getTitle())
                .previewContent(post.getPreviewContent())
                .previewImage(post.getPreviewImage())
                .createdDate(post.getCreatedDate().toLocalDate())
                .name(post.getMember().getName())
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .scrapCount(post.getScrapCount())
                .build());

        PageInfo pageInfo = PageInfo.toPageInfo(pageable, posts);
        PageResponse pageResponse = PageResponse.toPageResponse(pageInfo, postList.getContent());

        return SuccessResponse.of(pageResponse);
    }

    public SuccessResponse<PageResponse> getPosts(Part part, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Post> posts;

        posts = postList(part, pageable);

        Page<PostListRes> postList = posts.map(post -> PostListRes.builder()
                .id(post.getId())
                .title(post.getTitle())
                .previewContent(post.getPreviewContent())
                .previewImage(post.getPreviewImage())
                .createdDate(post.getCreatedDate().toLocalDate())
                .name(post.getMember().getName())
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .scrapCount(post.getScrapCount())
                .build());

        PageInfo pageInfo = PageInfo.toPageInfo(pageable, posts);
        PageResponse pageResponse = PageResponse.toPageResponse(pageInfo, postList.getContent());

        return SuccessResponse.of(pageResponse);
    }

    // 개발 관련 파트(WEB, ANDROID, SERVER) Grouping
    private Page<Post> postList(Part part, Pageable pageable) {
        Page<Post> posts;
        List<Part> partGroup;
        switch (part){
            case WEB, ANDROID, SERVER -> partGroup = List.of(Part.WEB, Part.ANDROID, Part.SERVER);
            default -> partGroup = List.of(part);
        }
        posts = postRepository.findByMemberPart(partGroup, pageable);

        return posts;
    }

    public SuccessResponse<FileUrlRes> uploadImages(MultipartFile multipartFile) {
        String filePath = fileService.uploadFile(multipartFile, DIRNAME);

        FileUrlRes fileUrlRes = FileUrlRes.builder()
                .fileUrl(filePath)
                .build();

        return SuccessResponse.of(fileUrlRes);
    }

    @Transactional
    public SuccessResponse<PostDetailsRes> getPostDetails(Long memberId, Long postId) {
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

        PostDetailsRes postDetailsRes = PostDetailsRes.builder()
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
                .writerInfo(PostDetailsRes.WriterInfo.builder()
                        .avatarImage(post.getMember().getAvatarImage())
                        .name(post.getMember().getName())
                        .generation(post.getMember().getGeneration())
                        .part(post.getMember().getPart())
                        .build())
                .build();

        return SuccessResponse.of(postDetailsRes);
    }
}
