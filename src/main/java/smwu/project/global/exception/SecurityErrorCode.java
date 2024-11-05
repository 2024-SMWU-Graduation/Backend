package smwu.project.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SecurityErrorCode implements ErrorCode {
    LOGIN_FAILED(400, "로그인에 실패했습니다."),
    WITHDRAWAL_USER(400, "탈퇴한 회원입니다."),
    EXPIRED_TOKEN(401, "만료된 토큰입니다.");

    private final int statusCode;
    private final String errorMessage;
}