package smwu.project.global.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FeedbackErrorCode implements ErrorCode {
    FEEDBACK_NOT_FOUND(404, "해당 피드백 데이터를 찾을 수 없습니다.");

    private final int statusCode;
    private final String message;
}
