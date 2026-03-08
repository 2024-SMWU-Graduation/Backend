package smwu.project.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import smwu.project.domain.entity.RandomQuestion;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TailQuestionResponseDto {
    private boolean isSuccess;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long questionId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String questionData;

    public static TailQuestionResponseDto of(RandomQuestion randomQuestion) {
        return TailQuestionResponseDto.builder()
                .isSuccess(true)
                .questionId(randomQuestion.getId())
                .questionData(randomQuestion.getQuestionData())
                .build();
    }

    public static TailQuestionResponseDto of() {
        return TailQuestionResponseDto.builder()
                .isSuccess(false)
                .build();
    }
}
