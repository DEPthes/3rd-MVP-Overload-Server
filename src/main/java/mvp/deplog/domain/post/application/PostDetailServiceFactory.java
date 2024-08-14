package mvp.deplog.domain.post.application;

import lombok.RequiredArgsConstructor;
import mvp.deplog.global.security.UserDetailsImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class PostDetailServiceFactory {

    private final List<PostDetailService<?>> postDetailServiceList;

    public PostDetailService<?> find(final UserDetailsImpl userDetails) {
        return postDetailServiceList.stream()
                .filter(v -> v.supports(userDetails))
                .findFirst()
                .orElseThrow();
    }
}
