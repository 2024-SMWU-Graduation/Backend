package smwu.project.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode{
    EMAIL_ALREADY_EXISTS(400, "이미 존재하는 이메일입니다."),
    PASSWORD_MISMATCH(400, "현재 비밀번호가 일치하지 않습니다."),
    PASSWORD_SAME_AS_OLD(400, "이전 비밀번호와 동일한 값입니다.")
    ;

    private final int statusCode;
    private final String message;
}
