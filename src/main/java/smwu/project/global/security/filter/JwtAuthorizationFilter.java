package smwu.project.global.security.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import smwu.project.global.exception.CustomSecurityException;
import smwu.project.global.exception.errorCode.SecurityErrorCode;
import smwu.project.global.jwt.JwtProvider;
import smwu.project.global.jwt.RefreshTokenService;
import smwu.project.global.security.UserDetailsServiceImpl;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * 토큰 검증
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtProvider.getJwtFromHeader(request, JwtProvider.AUTHORIZATION_HEADER);

        if(StringUtils.hasText(accessToken)) {
            if(jwtProvider.validateTokenInternal(request, accessToken)) {
                Claims info = jwtProvider.getUserInfoFromClaims(accessToken);
                String userEmail = info.getSubject();

                if(refreshTokenService.isRefreshTokenPresent(userEmail)) {
                    setAuthentication(userEmail);
                } else{
                    request.setAttribute("exception", new CustomSecurityException(SecurityErrorCode.INVALID_ACCESS_TOKEN));
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 인증 처리
     */
    private void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    /**
     * 인증 객체 생성
     */
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
