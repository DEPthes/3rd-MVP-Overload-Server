package mvp.deplog.global.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.auth.domain.respository.RefreshTokenRepository;
import mvp.deplog.domain.user.domain.User;
import mvp.deplog.domain.user.domain.repository.UserRepository;
import mvp.deplog.global.security.UserDetailsImpl;
import mvp.deplog.global.security.jwt.JwtProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// AccessToken을 이용한 인증 필터
@RequiredArgsConstructor
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper(); //5

    private final String NO_CHECK_URL = "/auth/login"; //1

    /**
     * 1. 리프레시 토큰이 오는 경우 -> 유효하면 AccessToken 재발급후, 필터 진행 X, 바로 튕기기
     *
     * 2. 리프레시 토큰은 없고 AccessToken만 있는 경우 -> 유저정보 저장후 필터 계속 진행
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().equals(NO_CHECK_URL)) {
            filterChain.doFilter(request, response);
            return; // 안 해주면 아래로 내려가서 계속 필터를 진행하게 됨
        }

        String refreshToken = jwtProvider
                .extractRefreshToken(request)
                .filter(jwtProvider::isTokenValid)
                .orElse(null); //2

        if(refreshToken != null){
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken); //3
            return;
        }

        checkAccessTokenAndAuthentication(request, response, filterChain);//4
    }

    private void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        jwtProvider.extractAccessToken(request).filter(jwtProvider::isTokenValid)
                .ifPresent(accessToken -> jwtProvider.extractEmail(accessToken)
                        .ifPresent(email -> userRepository.findByEmail(email)
                                .ifPresent(user -> saveAuthentication(user)
                                )
                )
        );

        filterChain.doFilter(request,response);
    }

    private void saveAuthentication(User user) {
        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));

        SecurityContext context = SecurityContextHolder.createEmptyContext();//5
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    private void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        refreshTokenRepository.findByRefreshToken(refreshToken)
                        .ifPresent(findRefreshToken ->
                                jwtProvider.sendAccessToken(response, jwtProvider.createAccessToken(findRefreshToken.getUserEmail()))
                        );

//        userRepository.findByRefreshToken(refreshToken).ifPresent(
//                users -> jwtProvider.sendAccessToken(response, jwtProvider.createAccessToken(users.getEmail()))
//        );


    }
}