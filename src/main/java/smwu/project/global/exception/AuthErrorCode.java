package smwu.project.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    INVALID_OAUTH2_PROVIDER(400, "지원하지 않는 로그인 방식입니다. ")
    ;

    private final int statusCode;
    private final String message;
}
