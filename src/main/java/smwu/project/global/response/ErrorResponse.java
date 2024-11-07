package smwu.project.global.response;

import smwu.project.global.exception.ErrorCode;

public class ErrorResponse<T> extends Response<T> {

    public ErrorResponse(ErrorCode errorCode) {
        super(errorCode.getStatusCode(), errorCode.getMessage(), null);
    }

    public ErrorResponse(ErrorCode errorCode, T data) {
        super(errorCode.getStatusCode(), errorCode.getMessage(), data);
    }
}
