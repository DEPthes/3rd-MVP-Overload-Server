package mvp.deplog.domain.auth.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mvp.deplog.domain.common.BaseEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "refresh_token")
public class RefreshToken extends BaseEntity {

    @Id
    @Column(name = "member_email", nullable = false)
    private String memberEmail;

    @Lob
    @Column(name = "refresh_token", columnDefinition = "TEXT", nullable = false)
    private String refreshToken;

    public RefreshToken updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    @Builder
    public RefreshToken(String memberEmail, String refreshToken) {
        this.memberEmail = memberEmail;
        this.refreshToken = refreshToken;
    }
}
