package smwu.project.domain.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RandomFeedbackRequestDto {
    private Long questionId;
    private float negativePercentage;
    private List<FeedbackTimelineRequestDto> timelines;
}
