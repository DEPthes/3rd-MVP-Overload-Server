package mvp.deplog.domain.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    MEMBER("ROLE_MEMBER", "회원"),
    ADMIN("ROLE_ADMIN", "관리자"),
    APPLICANT("ROLE_APPLICANT", "승인 대기자");

    String key;
    String value;
}
