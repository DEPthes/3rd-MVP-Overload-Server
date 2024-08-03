package mvp.deplog.infrastructure.mail.presentation;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.global.exception.ErrorResponse;
import mvp.deplog.infrastructure.mail.dto.MailCodeRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@Tag(name = "Mail API", description = "메일 관련 API입니다.")
public interface MailApi {

    @Operation(summary = "메일 발송 API", description = "메일을 발송합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "메일 발송 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MailCodeRes.class))}
            ),
            @ApiResponse(
                    responseCode = "400", description = "메일 발송 실패",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @GetMapping
    ResponseEntity<SuccessResponse<MailCodeRes>> sendMail(
            @Parameter(description = "이메일을 입력해주세요.", required = true) @RequestParam String email
    ) throws Exception;

    @Hidden
    @Operation(summary = "메일 검증 API", description = "메일을 검증합니다. 사용자가 링크 클릭 시 호출됩니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "메일 검증 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Message.class))}
            ),
            @ApiResponse(
                    responseCode = "400", description = "메일 검증 실패",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}
            )
    })
    @GetMapping(value = "/verify")
    void verify(
            @Parameter(description = "메일 발송 시 응답받은 코드를 사용해주세요.", required = true) @RequestParam String code,
            HttpServletResponse response
    ) throws IOException;
}
