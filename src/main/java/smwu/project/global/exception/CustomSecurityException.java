package smwu.project.global.exception;

import smwu.project.global.exception.errorCode.ErrorCode;

public class CustomSecurityException extends CustomException {
    public CustomSecurityException(ErrorCode errorCode) {
        super(errorCode);
    }
}
