package mvp.deplog.global.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mvp.deplog.domain.auth.domain.respository.RefreshTokenRepository;
import mvp.deplog.domain.auth.exception.RefreshTokenNotFoundException;
import mvp.deplog.domain.user.domain.repository.UserRepository;
import mvp.deplog.global.security.jwt.JwtProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class LoginSuccessJWTProvideHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String email = extractEmail(authentication);
        String accessToken = jwtProvider.createAccessToken(email);
        String refreshToken = jwtProvider.createRefreshToken();

        jwtProvider.sendAccessAndRefreshToken(response, accessToken, refreshToken);

//        userRepository.findByEmail(email)
//                .ifPresent(users -> users.updateRefreshToken(refreshToken));

        userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일로 유저를 찾을 수 없습니다: " + email));

        refreshTokenRepository.findByUserEmail(email)
                .ifPresent(findRefreshToken -> findRefreshToken.updateRefreshToken(refreshToken));

        // Description : 해당 클래스는 로그인 성공 시이므로 else의 경우 예외 처리 하면 안 됨
//                .ifPresentOrElse(
//                        findRefreshToken -> findRefreshToken.updateRefreshToken(refreshToken),
//                        () -> new RefreshTokenNotFoundException("해당 이메일로 토큰을 찾을 수 없습니다: " + email)
//                );

        log.info( "로그인에 성공합니다. email: {}" , email);
        log.info( "AccessToken 을 발급합니다. AccessToken: {}" ,accessToken);
        log.info( "RefreshToken 을 발급합니다. RefreshToken: {}" ,refreshToken);

        response.getWriter().write("success");
    }

    private String extractEmail(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
