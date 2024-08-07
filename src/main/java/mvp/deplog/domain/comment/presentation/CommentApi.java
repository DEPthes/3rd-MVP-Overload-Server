package mvp.deplog.domain.comment.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mvp.deplog.domain.comment.dto.request.CommentReq;
import mvp.deplog.domain.comment.dto.response.CommentListRes;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.global.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Comment API", description = "댓글 관련 API입니다.")
public interface CommentApi {

    @Operation(summary = "댓글 작성 API", description = "댓글 작성을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "댓글 작성 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CommentListRes.class))}
            ),
            @ApiResponse(
                    responseCode = "400", description = "댓글 작성 실패",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @PostMapping
    ResponseEntity<SuccessResponse<Message>> createComment(
            @Parameter(description = "Schemas의 CommentReq를 참고해주세요.", required = true) @RequestBody CommentReq commentReq);
}
