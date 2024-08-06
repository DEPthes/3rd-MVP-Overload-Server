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
import mvp.deplog.global.common.PageResponse;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.global.exception.ErrorResponse;
import mvp.deplog.global.security.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "게시글 전체 목록 조회 API", description = "게시글 전체 목록을 출력합니다.")
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
    ResponseEntity<SuccessResponse<PageResponse>> getAllPost(
            @Parameter(description = "조회할 페이지의 번호를 입력해주세요. **page는 1부터 시작합니다**", required = true) @RequestParam(value = "page", defaultValue = "1") Integer page,
            @Parameter(description = "한 페이지 당 최대 항목 개수를 입력해주세요. 기본값은 10입니다.", required = true) @RequestParam(value = "size", defaultValue = "10") Integer size
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
    @GetMapping("/{part}")
    ResponseEntity<SuccessResponse<PageResponse>> getPartPost(
            @Parameter(description = "보고싶은 게시글 목록의 파트를 입력해주세요.", required = true) @PathVariable(value = "part") Part part,
            @Parameter(description = "조회할 페이지의 번호를 입력해주세요. **page는 1부터 시작합니다**", required = true) @RequestParam(value = "page", defaultValue = "1") Integer page,
            @Parameter(description = "한 페이지 당 최대 항목 개수를 입력해주세요. 기본값은 10입니다.", required = true) @RequestParam(value = "size", defaultValue = "10") Integer size
    );
}
