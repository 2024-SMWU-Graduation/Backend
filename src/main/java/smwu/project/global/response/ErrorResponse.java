package smwu.project.global.response;

import smwu.project.global.exception.ErrorCode;

public class ErrorResponse extends Response {
    private ErrorCode errorCode;

    public ErrorResponse(ErrorCode errorCode) {
        super(errorCode.getStatusCode(), errorCode.getErrorMessage(), null);
    }
}
