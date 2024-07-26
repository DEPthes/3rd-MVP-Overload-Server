package mvp.deplog.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.auth.domain.respository.RefreshTokenRepository;
import mvp.deplog.domain.user.domain.repository.UserRepository;
import mvp.deplog.global.security.UserDetailsServiceImpl;
import mvp.deplog.global.security.filter.JsonAuthenticationFilter;
import mvp.deplog.global.security.filter.JwtAuthenticationProcessingFilter;
import mvp.deplog.global.security.handler.LoginFailureHandler;
import mvp.deplog.global.security.handler.LoginSuccessJWTProvideHandler;
import mvp.deplog.global.security.jwt.JwtProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    private static final String[] WHITE_LIST = {
            "/swagger", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/auth/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(WHITE_LIST).permitAll()
                .anyRequest().authenticated()
                )

                .logout((logout) -> logout
                        .logoutSuccessUrl("/auth/login")
                        .invalidateHttpSession(true))

                .addFilterAfter(jsonAuthenticationFilter(), LogoutFilter.class)
                .addFilterBefore(jwtAuthenticationProcessingFilter(), JsonAuthenticationFilter.class)
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        DaoAuthenticationProvider provider = daoAuthenticationProvider(); // ProviderManager(AuthenticationManager)의 AuthenticationProvider - DaoProvider 등록
//        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public LoginSuccessJWTProvideHandler loginSuccessJWTProvideHandler(){
        return new LoginSuccessJWTProvideHandler(jwtProvider, userRepository, refreshTokenRepository);
    }

    @Bean
    public LoginFailureHandler loginFailureHandler(){
        return new LoginFailureHandler();
    }

    @Bean
    public JsonAuthenticationFilter jsonAuthenticationFilter() throws Exception {
        JsonAuthenticationFilter jsonAuthenticationFilter = new JsonAuthenticationFilter(objectMapper);
        jsonAuthenticationFilter.setAuthenticationManager(authenticationManager());
        jsonAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessJWTProvideHandler());
        jsonAuthenticationFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return jsonAuthenticationFilter;
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter(){
        return new JwtAuthenticationProcessingFilter(jwtProvider, userRepository, refreshTokenRepository);
    }
}
