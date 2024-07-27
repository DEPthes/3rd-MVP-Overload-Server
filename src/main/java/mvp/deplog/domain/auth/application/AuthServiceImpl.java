package mvp.deplog.domain.auth.application;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.auth.dto.SignUpReq;
import mvp.deplog.domain.user.domain.User;
import mvp.deplog.domain.user.domain.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public ResponseEntity<?> signUp(SignUpReq signUpReq) {

        User user = User.builder()
                .email(signUpReq.getEmail())
                .password(passwordEncoder.encode(signUpReq.getPassword()))
                .username(signUpReq.getUsername())
                .part(signUpReq.getPart())
                .generation(signUpReq.getGeneration())
                .build();

        userRepository.save(user);
        return ResponseEntity.ok("회원가입 완료");
    }
}
