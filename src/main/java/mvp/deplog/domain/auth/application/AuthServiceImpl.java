package mvp.deplog.domain.auth.application;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.auth.dto.mapper.MemberAuthMapper;
import mvp.deplog.domain.auth.dto.request.LoginReq;
import mvp.deplog.domain.auth.dto.request.ModifyPasswordReq;
import mvp.deplog.domain.auth.dto.response.EmailDuplicateCheckRes;
import mvp.deplog.domain.auth.dto.response.LoginRes;
import mvp.deplog.domain.auth.dto.request.JoinReq;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.domain.member.domain.Member;
import mvp.deplog.domain.member.domain.repository.MemberRepository;
import mvp.deplog.global.security.jwt.JwtTokenProvider;
import mvp.deplog.infrastructure.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${jwt.refresh.expiration}")
    private long refreshTokenValidityInSeconds;

    private static String RT_PREFIX = "RT_";

    private final RedisUtil redisUtil;
    private final AuthenticationManager authenticationManager;
    private final MemberAuthMapper memberAuthMapper;

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public SuccessResponse<Message> join(JoinReq joinReq) {
        String email = joinReq.getEmail();
        checkVerify(joinReq.getEmail());

        if (memberRepository.existsByEmail(email))
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");

        Member member = memberAuthMapper.joinToMember(joinReq);
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

        String findRefreshToken = redisUtil.getData(RT_PREFIX + email);
        redisUtil.setDataExpire(RT_PREFIX + email, refreshToken, refreshTokenValidityInSeconds);

        LoginRes loginRes = LoginRes.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return SuccessResponse.of(loginRes);
    }

    @Override
    public SuccessResponse<EmailDuplicateCheckRes> checkEmailDuplicate(String email) {
        EmailDuplicateCheckRes emailDuplicateCheckRes = EmailDuplicateCheckRes.builder()
                .availability(!memberRepository.existsByEmail(email))
                .build();

        return SuccessResponse.of(emailDuplicateCheckRes);
    }

    @Override
    @Transactional
    public SuccessResponse<Message> modifyPassword(ModifyPasswordReq modifyPasswordReq) {
        String email = modifyPasswordReq.getEmail();
        checkVerify(email);

        memberRepository.findByEmail(email)
                .ifPresentOrElse(
                        member -> member.updatePassword(passwordEncoder.encode(modifyPasswordReq.getPassword())),
                        () -> {
                            throw new IllegalArgumentException("해당 이메일로 가입된 계정이 존재하지 않습니다.");
                        }
                );

        Message message = Message.builder()
                .message("비밀번호 변경이 완료되었습니다.")
                .build();

        return SuccessResponse.of(message);
    }

    private void checkVerify(String email) {
        String data = redisUtil.getData(email + "_verify");
        if (data == null)
            throw new IllegalArgumentException("인증이 필요한 이메일입니다.");
        redisUtil.deleteData(email + "_verify");
    }
}
