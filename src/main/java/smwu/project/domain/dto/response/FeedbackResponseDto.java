package smwu.project.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import smwu.project.domain.entity.IntroduceFeedback;
import smwu.project.domain.entity.IntroduceInterview;
import smwu.project.global.util.FormatUtil;

import java.util.List;

@Getter
@Builder
public class FeedbackResponseDto {
    private Long feedbackId;
    private String videoUrl;
    private float negativePercentage;
    private List<String> timelines;
    private String analyzeUrl;

    public static FeedbackResponseDto of(IntroduceFeedback feedback, IntroduceInterview interview) {
        return FeedbackResponseDto.builder()
                .feedbackId(feedback.getId())
                .videoUrl(interview.getVideoUrl())
                .negativePercentage(feedback.getNegativePercentage())
                .timelines(FormatUtil.parseTimelines(feedback.getTimelines()))
                .analyzeUrl(feedback.getAnalyzeUrl())
                .build();
    }
}
