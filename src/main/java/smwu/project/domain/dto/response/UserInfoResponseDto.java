package smwu.project.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import smwu.project.domain.entity.User;
import smwu.project.domain.enums.OAuthProvider;

@Getter
@Builder
public class UserInfoResponseDto {
    private Long userId;
    private String email;
    private String name;
    private OAuthProvider oAuthProvider;

    public static UserInfoResponseDto of(User user) {
        return UserInfoResponseDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .oAuthProvider(user.getOAuthProvider())
                .build();
    }
}
