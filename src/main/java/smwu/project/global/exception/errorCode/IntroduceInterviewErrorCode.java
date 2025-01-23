package smwu.project.global.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum IntroduceInterviewErrorCode implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 자기소개 인터뷰를 찾을 수 없습니다.")
    ;

    private final int statusCode;
    private final String message;
}
