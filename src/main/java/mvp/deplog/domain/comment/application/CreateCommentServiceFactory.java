package mvp.deplog.domain.comment.application;

import lombok.RequiredArgsConstructor;
import mvp.deplog.global.security.UserDetailsImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CreateCommentServiceFactory {

    private final List<CreateCommentService> createCommentServiceList;

    public CreateCommentService find(UserDetailsImpl userDetails, Long parentCommentId) {
        return createCommentServiceList.stream()
                .filter(v -> v.supports(userDetails, parentCommentId))
                .findFirst()
                .orElseThrow();
    }
}
