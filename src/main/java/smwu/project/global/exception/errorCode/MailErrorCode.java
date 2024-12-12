package smwu.project.global.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MailErrorCode implements ErrorCode {
    EMAIL_ALREADY_EXISTS(400, "이미 존재하는 이메일입니다."),
    INVALID_MAIL_SETTING(400, "이메일 전송 중 오류가 발생했습니다."),
    VERIFICATION_CODE_NOT_FOUND(404, "인증 코드를 한 번 더 요청해주세요."),
    INVALID_VERIFICATION_CODE(400, "인증 코드가 일치하지 않습니다."),
    ;

    private final int statusCode;
    private final String message;
}
