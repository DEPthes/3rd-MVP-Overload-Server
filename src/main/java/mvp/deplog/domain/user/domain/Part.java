package mvp.deplog.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Part {

    PLAN("PLAN", "기획"),
    DESIGN("DESIGN", "디자인"),
    ANDROID("ANDROID", "안드로이드"),
    WEB("WEB", "웹"),
    SERVER("SERVER", "서버");

    String key;
    String value;
}
