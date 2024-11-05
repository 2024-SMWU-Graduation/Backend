package smwu.project.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import smwu.project.domain.dto.request.LoginRequestDto;
import smwu.project.domain.entity.User;
import smwu.project.domain.enums.UserStatus;
import smwu.project.global.exception.SecurityErrorCode;
import smwu.project.global.jwt.JwtProvider;
import smwu.project.global.security.UserDetailsImpl;
import smwu.project.global.util.ResponseUtil;

import java.io.IOException;

@Slf4j(topic = "로그인 필터")
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtProvider jwtProvider;

    public LoginFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
        setFilterProcessesUrl("/api/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword(), null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        User loginUser = userDetails.getUser();

        if(loginUser.getUserStatus() == UserStatus.ACTIVATE) {
            String userEmail = loginUser.getEmail();
            String accessToken = jwtProvider.createAccessToken(userEmail, loginUser.getUserRole());
            String refreshToken = jwtProvider.createRefreshToken(userEmail);

            response.addHeader(JwtProvider.AUTHORIZATION_HEADER, accessToken);
            response.addHeader(JwtProvider.REFRESH_HEADER, refreshToken);

            ResponseUtil.writeJsonResponse(response, HttpStatus.OK, "로그인 성공", authResult.getName());
        } else {
            ResponseUtil.writeJsonErrorResponse(response, SecurityErrorCode.WITHDRAWAL_USER);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        log.debug("로그인 실패 : {}", failed.getMessage());
        ResponseUtil.writeJsonErrorResponse(response, SecurityErrorCode.LOGIN_FAILED);
    }
}
