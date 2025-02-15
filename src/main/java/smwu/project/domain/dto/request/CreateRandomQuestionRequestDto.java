package smwu.project.domain.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateRandomQuestionRequestDto {
    private Long interviewId;
    private String questionData;
}
