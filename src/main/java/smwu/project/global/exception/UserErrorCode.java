package smwu.project.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode{
    EMAIL_ALREADY_EXISTS(400, "이미 존재하는 이메일입니다.");

    private final int statusCode;
    private final String message;
}
