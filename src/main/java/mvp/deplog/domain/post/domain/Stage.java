package mvp.deplog.domain.post.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Stage {

    TEMP("TEMP", "임시 저장"),
    PUBLISHED("PUBLISHED", "발행");

    String key;
    String value;
}
