package mvp.deplog.domain.post.application;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.comment.domain.Comment;
import mvp.deplog.domain.comment.domain.repository.CommentRepository;
import mvp.deplog.domain.comment.dto.response.CommentListRes;
import mvp.deplog.domain.likes.domain.repository.LikesRepository;
import mvp.deplog.domain.member.domain.Member;
import mvp.deplog.domain.member.domain.Part;
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
import mvp.deplog.infrastructure.s3.S3FileUtil;
import mvp.deplog.infrastructure.s3.application.FileService;
import mvp.deplog.infrastructure.s3.dto.response.FileUrlRes;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
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

    public SuccessResponse<PostDetailsRes> getPostDetails(Member member, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 id의 게시글을 찾을 수 없습니다: " + postId));
        Long currentMemberId = member.getId();
        Long writerId = post.getMember().getId();
        boolean sameUser = (currentMemberId != null && currentMemberId.equals(writerId));

        // 해당 게시글의 댓글 리스트 조회
        List<Comment> commentList = commentRepository.findByPostId(postId);

        List<CommentListRes> commentDetails = commentList.stream()
                .map(comment -> CommentListRes.builder()
                        .commentId(comment.getId())
                        .avatarImage(comment.getPost().getMember().getAvatarImage())    // 아바타 이미지에 대한 경로 고민 필요
                        .nickname(comment.getNickname())
                        .createdDate(comment.getCreatedDate().toLocalDate())
                        .content(comment.getContent())
                        .build())
                .collect(Collectors.toList());

        // 게시글 내용 Markdown으로 변환
        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String contentHtml = renderer.render(parser.parse(post.getContent()));

        // Tagging Entity에 Tag 목록 조회
        List<String> tags = taggingRepository.findByPost(postId);


        PostDetailsRes postDetailsRes = PostDetailsRes.builder()
                .postId(post.getId())
                .mine(sameUser)
                .title(post.getTitle())
                .content(contentHtml)
                .tagList(tags)
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .scrapCount(post.getScrapCount())
//                .liked(post.get)
//                .scraped()
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
