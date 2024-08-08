package mvp.deplog.domain.scrap.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mvp.deplog.domain.comment.dto.response.CommentRes;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.global.exception.ErrorResponse;
import mvp.deplog.global.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
}