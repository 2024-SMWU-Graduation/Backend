package smwu.project.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import smwu.project.domain.entity.RandomQuestion;

import java.time.LocalDateTime;

@Getter
@Builder
public class RandomQuestionResponseDto {
    Long questionId;
    String videoUrl;
    LocalDateTime createdAt;

    public static RandomQuestionResponseDto of(RandomQuestion randomQuestion) {
        return RandomQuestionResponseDto.builder()
                .questionId(randomQuestion.getId())
                .videoUrl(randomQuestion.getVideoUrl())
                .createdAt(randomQuestion.getCreatedAt())
                .build();
    }
}
