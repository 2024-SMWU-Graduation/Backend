package smwu.project.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import smwu.project.domain.entity.RandomFeedback;

import java.util.List;

@Getter
@Builder
public class RandomFeedbackDetailResponseDto {
    private String questionData;
    private String videoUrl;
    private float negativePercentage;
    private List<FeedbackTimelineResponseDto> timelines;
    private String analyzeUrl;

    public static RandomFeedbackDetailResponseDto of(RandomFeedback randomFeedback) {
        return RandomFeedbackDetailResponseDto.builder()
                .questionData(randomFeedback.getRandomQuestion().getQuestionData())
                .videoUrl(randomFeedback.getRandomQuestion().getVideoUrl())
                .negativePercentage(randomFeedback.getNegativePercentage())
                .timelines(randomFeedback.getTimelines().stream().map(FeedbackTimelineResponseDto::of).toList())
                .analyzeUrl(randomFeedback.getAnalyzeUrl())
                .build();
    }
}
