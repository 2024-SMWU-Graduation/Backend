package smwu.project.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import smwu.project.domain.entity.User;
import smwu.project.domain.enums.OAuthProvider;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserInfoResponseDto {
    private Long userId;
    private String email;
    private String name;
    private OAuthProvider oAuthProvider;
    private LocalDateTime createdAt;

    public static UserInfoResponseDto of(User user) {
        return UserInfoResponseDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .oAuthProvider(user.getOAuthProvider())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
