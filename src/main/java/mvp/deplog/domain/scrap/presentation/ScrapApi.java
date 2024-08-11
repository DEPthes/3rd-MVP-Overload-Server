package mvp.deplog.domain.scrap.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mvp.deplog.domain.post.dto.response.PostListRes;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.PageResponse;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.global.exception.ErrorResponse;
import mvp.deplog.global.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Scrap API", description = "스크랩 관련 API 입니다.")
public interface ScrapApi {

    @Operation(summary = "스크랩 API", description = "게시글을 스크랩합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "스크랩 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))}
            ),
            @ApiResponse(
                    responseCode = "400", description = "스크랩 실패",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @PostMapping("/{postId}")
    ResponseEntity<SuccessResponse<Message>> scrapPost(
            @Parameter(description = "Access Token을 입력해주세요.", required = true) @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Parameter(description = "스크랩 할 게시글의 id를 입력해주세요.", required = true) @PathVariable(value = "postId")Long postId
    );

    @Operation(summary = "스크랩 해제 API", description = "스크랩한 게시글을 해제합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "스크랩 해제 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))}
            ),
            @ApiResponse(
                    responseCode = "400", description = "스크랩 해제 실패",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @DeleteMapping("/{postId}")
    ResponseEntity<SuccessResponse<Message>> deleteScrapPost(
            @Parameter(description = "Access Token을 입력해주세요.", required = true) @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Parameter(description = "스크랩을 해제할 게시글의 id를 입력해주세요.", required = true) @PathVariable(value = "postId")Long postId
    );

    @Operation(summary = "스크랩 게시글 조회 API", description = "회원이 스크랩한 게시글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "0", description = "조회 성공 - 페이징 dataList 구성",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PostListRes.class)))}
            ),
            @ApiResponse(
                    responseCode = "200", description = "스크랩 게시글 조회 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PageResponse.class))}
            ),
            @ApiResponse(
                    responseCode = "400", description = "스크랩 게시글 조회 실패",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @GetMapping
    ResponseEntity<SuccessResponse<PageResponse>> getScrapPosts(
            @Parameter(description = "Access Token을 입력해주세요.", required = true) @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Parameter(description = "조회할 페이지의 번호를 입력해주세요. **page는 1부터 시작합니다**", required = true) @RequestParam(value = "page", defaultValue = "1") Integer page,
            @Parameter(description = "한 페이지 당 최대 항목 개수를 입력해주세요. 기본값은 5입니다.", required = true) @RequestParam(value = "size", defaultValue = "5") Integer size
    );
}