package mvp.deplog.domain.post.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DraftListRes {

    private Long id;

    private String title;

    private LocalDate createdDate;
}
