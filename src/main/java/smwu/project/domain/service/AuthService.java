package smwu.project.domain.service;

import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smwu.project.domain.repository.UserRepository;
import smwu.project.domain.entity.User;
import smwu.project.global.exception.CustomException;
import smwu.project.global.exception.errorCode.AuthErrorCode;
import smwu.project.global.jwt.JwtProvider;
import smwu.project.global.jwt.RefreshTokenService;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;

    /**
     * Access Token, RefreshToken 재발급
     */
    @Transactional
    public void reissue(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = jwtProvider.getJwtFromHeader(request, JwtProvider.AUTHORIZATION_HEADER);
        String refreshToken = jwtProvider.getJwtFromHeader(request, JwtProvider.REFRESH_HEADER);

        // 액세스가 만료되었는지 확인
        if (!jwtProvider.isExpiredAccessToken(accessToken)) {
            throw new CustomException(AuthErrorCode.NOT_EXPIRED_ACCESS_TOKEN);
        }

        // 리프레시 토큰 검증
        if (jwtProvider.validateTokenInternal(request, refreshToken)) {
            String userEmail = jwtProvider.getUserInfoFromClaims(refreshToken).getSubject();

            refreshTokenService.checkValidRefreshToken(userEmail, refreshToken);

            // 리프레시 토큰 기반으로 유저 찾기
            User findUser = userRepository.findByEmailOrElseThrow(userEmail);

            // 새 토큰 발급
            String newAccessToken = jwtProvider.createAccessToken(findUser.getEmail(),
                    findUser.getUserRole());
            String newRefreshToken = jwtProvider.createRefreshToken(findUser.getEmail());

            refreshTokenService.saveRefreshTokenInfo(findUser.getEmail(), newRefreshToken);

            response.addHeader(JwtProvider.AUTHORIZATION_HEADER, newAccessToken);
            response.addHeader(JwtProvider.REFRESH_HEADER, newRefreshToken);

        } else {
            throw new CustomException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }
    }
}
