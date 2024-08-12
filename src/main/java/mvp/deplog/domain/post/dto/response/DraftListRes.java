package mvp.deplog.domain.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DraftListRes {

    @Schema(type = "Long", example = "1", description = "임시 저장 게시글 아이디입니다.")
    private Long id;

    @Schema(type = "String", example = "임시 저장 게시글 제목", description= "임시 저장 게시글 제목입니다.")
    private String title;

    @Schema(type = "LocalDate", example = "2024-08-05", description= "임시 저장 게시글 작성 날짜입니다.")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate createdDate;
}
