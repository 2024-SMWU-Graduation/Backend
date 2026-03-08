package smwu.project.global.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InterviewErrorCode implements ErrorCode {
    INTERVIEW_NOT_FOUND(404, "해당 면접 데이터를 찾을 수 없습니다."),
    INTERVIEW_UNAUTHORIZED_ACCESS(403, "해당 인터뷰에 접근할 수 있는 권한이 없습니다.")
    ;

    private final int statusCode;
    private final String message;
}
