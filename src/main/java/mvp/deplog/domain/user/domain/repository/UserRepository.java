package mvp.deplog.domain.user.domain.repository;

import mvp.deplog.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
//    Optional<User> findByPhoneNum(String phoneNum);
//    boolean existsByPhoneNum(String phoneNum);
//    Optional<User> findByRefreshToken(String refreshToke);
}
