package smwu.project.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import smwu.project.domain.entity.FeedbackTimeline;

@Getter
@Builder
public class FeedbackTimelineResponseDto {
    private String startTime;
    private String endTime;
    private String intensity;

    public static FeedbackTimelineResponseDto of(FeedbackTimeline feedbackTimeline) {
        return FeedbackTimelineResponseDto.builder()
                .startTime(feedbackTimeline.getStartTime())
                .endTime(feedbackTimeline.getEndTime())
                .intensity(feedbackTimeline.getIntensity())
                .build();
    }
}
