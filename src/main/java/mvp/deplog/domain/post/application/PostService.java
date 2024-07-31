package mvp.deplog.domain.post.application;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.block.domain.Block;
import mvp.deplog.domain.block.domain.repository.BlockRepository;
import mvp.deplog.domain.block.dto.BlockReq;
import mvp.deplog.domain.member.domain.Member;
import mvp.deplog.domain.post.domain.Post;
import mvp.deplog.domain.post.domain.Stage;
import mvp.deplog.domain.post.domain.repository.PostRepository;
import mvp.deplog.domain.post.dto.PostReq;
import mvp.deplog.domain.tag.domain.Tag;
import mvp.deplog.domain.tag.domain.repository.TagRepository;
import mvp.deplog.domain.tagging.Tagging;
import mvp.deplog.domain.tagging.repository.TaggingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {
    private final PostRepository postRepository;
    private final BlockRepository blockRepository;
    private final TagRepository tagRepository;
    private final TaggingRepository taggingRepository;

    @Transactional
    public ResponseEntity<String> createPost(PostReq postReq, Member member) {
        Post post = Post.builder()
                        .member(member)
                        .title(postReq.getTitle())
                        .stage(Stage.PUBLISHED)
                        .build();

        // 게시글 저장
        postRepository.save(post);

        // 블록 저장
        for (BlockReq blockReq : postReq.getBlockList()) {
            Block block = Block.builder()
                    .post(post)
                    .content(blockReq.getContent())
                    .sequence(blockReq.getSequence())
                    .isImage(blockReq.isImage())
                    .build();
            blockRepository.save(block);
        }

        // 태그 저장 & Tagging 엔티티 연결
        for(String tagName : postReq.getTagNameList()) {
            Tag tag = tagRepository.findByName(tagName)
                    .orElseGet(() -> tagRepository.save(Tag.builder().name(tagName).build()));

            Tagging tagging = Tagging.builder()
                                    .post(post)
                                    .tag(tag)
                                    .build();
            taggingRepository.save(tagging);
        }

        return ResponseEntity.ok("게시글 작성 완료");
    }
}
