package smwu.project.global.security;

import lombok.Builder;
import lombok.Getter;
import smwu.project.domain.entity.User;
import smwu.project.domain.enums.OAuthProvider;
import smwu.project.domain.enums.UserRole;
import smwu.project.domain.enums.UserStatus;
import smwu.project.global.exception.AuthErrorCode;
import smwu.project.global.exception.CustomException;

import java.util.Map;

@Getter
@Builder
public class OAuth2UserInfo {
    private String registrationId;
    private String email;
    private String name;

    public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId) {
            case "kakao" -> ofKakao(registrationId, attributes);
            default -> throw new CustomException(AuthErrorCode.INVALID_OAUTH2_PROVIDER);
        };
    }

    private static OAuth2UserInfo ofKakao(String registrationId, Map<String, Object> attributes) {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        return OAuth2UserInfo.builder()
                .registrationId(registrationId)
                .email((String) account.get("email"))
                .name((String) profile.get("nickname"))
                .build();
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .oAuthProvider(OAuthProvider.fromString(registrationId))
                .userRole(UserRole.USER)
                .userStatus(UserStatus.ACTIVATE)
                .build();
    }
}
