package mvp.deplog.domain.post.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mvp.deplog.domain.member.domain.Part;
import mvp.deplog.domain.post.dto.request.PostListReq;
import mvp.deplog.domain.post.dto.response.CreatePostRes;
import mvp.deplog.domain.post.dto.request.PostReq;
import mvp.deplog.domain.post.dto.response.PostListRes;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.global.exception.ErrorResponse;
import mvp.deplog.global.security.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Post API", description = "게시글 관련 API입니다.")
public interface PostApi {

    @Operation(summary = "게시글 작성 API", description = "게시글 작성을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "게시글 작성 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))}
            ),
            @ApiResponse(
                    responseCode = "400", description = "게시글 작성 실패",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @PostMapping
    ResponseEntity<SuccessResponse<CreatePostRes>> createPost(
            @Parameter(description = "Access Token을 입력해주세요.", required = true) @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Parameter(description = "Schemas의 PostReq를 참고해주세요.", required = true) @RequestBody PostReq postReq
    );

    @Operation(summary = "게시글 목록 조회 API", description = "게시글 목록을 파트에 맞춰 출력합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "게시글 목록 조회 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PostListRes.class))}
            ),
            @ApiResponse(
                    responseCode = "400", description = "게시글 목록 조회 실패",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @GetMapping
    ResponseEntity<SuccessResponse<Page<PostListRes>>> getAllPost(
            @Parameter(description = "Schemas의 PostListReq를 참고해주세요.", required = false) @RequestBody PostListReq postListReq,
            @RequestParam int page,
            @RequestParam int size
    );
}
