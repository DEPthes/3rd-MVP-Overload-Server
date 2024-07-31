package mvp.deplog.domain.auth.application;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.auth.domain.RefreshToken;
import mvp.deplog.domain.auth.domain.respository.RefreshTokenRepository;
import mvp.deplog.domain.auth.dto.LoginReq;
import mvp.deplog.domain.auth.dto.LoginRes;
import mvp.deplog.domain.auth.dto.JoinReq;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.domain.member.domain.Member;
import mvp.deplog.domain.member.domain.repository.MemberRepository;
import mvp.deplog.global.security.jwt.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthServiceImpl implements AuthService{

    private final AuthenticationManager authenticationManager;

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public SuccessResponse<Message> join(JoinReq joinReq) {
        Member member = Member.builder()
                .email(joinReq.getEmail())
                .password(passwordEncoder.encode(joinReq.getPassword()))
                .name(joinReq.getName())
                .part(joinReq.getPart())
                .generation(joinReq.getGeneration())
                .build();

        memberRepository.save(member);

        Message message = Message.builder()
                .message("회원 가입이 완료되었습니다.")
                .build();

        return SuccessResponse.of(message);
    }

    @Override
    @Transactional
    public SuccessResponse<LoginRes> login(LoginReq loginReq) {
        String email = loginReq.getEmail();
        String password = loginReq.getPassword();

        // 인증
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtTokenProvider.createAccessToken(email);
        String refreshToken = jwtTokenProvider.createRefreshToken();

        memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일로 유저를 찾을 수 없습니다: " + email));

        refreshTokenRepository.findByMemberEmail(email)
                .ifPresentOrElse(
                        findRefreshToken -> findRefreshToken.updateRefreshToken(refreshToken),
                        () -> refreshTokenRepository.save(
                                RefreshToken.builder()
                                        .memberEmail(loginReq.getEmail())
                                        .refreshToken(refreshToken)
                                        .build()
                        )
                );

        refreshTokenRepository.save(RefreshToken.builder()
                .memberEmail(loginReq.getEmail())
                .refreshToken(refreshToken)
                .build()
        );

        LoginRes loginRes = LoginRes.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return SuccessResponse.of(loginRes);
    }
}
