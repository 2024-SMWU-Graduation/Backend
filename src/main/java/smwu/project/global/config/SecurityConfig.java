package smwu.project.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import smwu.project.global.jwt.JwtProvider;
import smwu.project.global.jwt.RefreshTokenService;
import smwu.project.global.security.CustomOAuth2UserService;
import smwu.project.global.security.OAuth2SuccessHandler;
import smwu.project.global.security.UserDetailsServiceImpl;
import smwu.project.global.security.filter.CustomAuthenticationEntryPoint;
import smwu.project.global.security.filter.JwtAuthorizationFilter;
import smwu.project.global.security.filter.LoginFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserDetailsServiceImpl userDetailsService;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthorizationFilter authorizationFilter() {
        return new JwtAuthorizationFilter(jwtProvider, refreshTokenService, userDetailsService);
    }

    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter filter = new LoginFilter(jwtProvider, refreshTokenService);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization");
        configuration.addExposedHeader("RefreshToken");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable); // CSRF 설정
        http.cors(corsConfigurer -> corsConfigurer.configurationSource(
                corsConfigurationSource())); // CORS 설정

        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .permitAll() // resource 접근 허용 설정
                .requestMatchers("/users/login-page").permitAll()
                .requestMatchers("/error").permitAll()
                .requestMatchers("/favicon.ico").permitAll()
                .requestMatchers("/api/mail").permitAll()
                .requestMatchers("/api/mail/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users/signup").permitAll() // 회원가입 접근 허용
                .requestMatchers(HttpMethod.POST, "/api/auth/reissue").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/interview").permitAll() // 임시
                .anyRequest().authenticated()
        );

        http.oauth2Login(oauth -> oauth
                .userInfoEndpoint(c -> c.userService(oAuth2UserService))
                .successHandler(oAuth2SuccessHandler)
        );

        http.addFilterBefore(authorizationFilter(), LoginFilter.class);
        http.addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(authenticationEntryPoint));

        return http.build();
    }
}
