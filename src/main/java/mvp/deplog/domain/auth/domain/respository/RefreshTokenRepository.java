package mvp.deplog.domain.auth.domain.respository;

import mvp.deplog.domain.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByUserEmail(String userEmail);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
