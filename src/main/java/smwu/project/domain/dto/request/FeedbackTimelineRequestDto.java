package smwu.project.domain.dto.request;

import lombok.Getter;
import smwu.project.domain.entity.FeedbackTimeline;

@Getter
public class FeedbackTimelineRequestDto {
    private String startTime;
    private String endTime;
    private String intensity;

    public FeedbackTimeline toEntity() {
        return FeedbackTimeline.builder()
                .startTime(startTime)
                .endTime(endTime)
                .intensity(intensity)
                .build();
    }
}
