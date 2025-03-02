package smwu.project.domain.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IntroduceFeedbackRequestDto {
    private Long interviewId;
    private float negativePercentage;
    private List<FeedbackTimelineRequestDto> timelines;
}
