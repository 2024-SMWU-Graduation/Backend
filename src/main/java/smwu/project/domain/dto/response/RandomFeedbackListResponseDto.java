package smwu.project.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import smwu.project.domain.entity.RandomFeedback;
import smwu.project.domain.entity.RandomInterview;

import java.util.List;

@Getter
@Builder
public class RandomFeedbackListResponseDto {
    private Long interviewId;
    private String title;
    private List<RandomFeedbackDetailResponseDto> feedbackList;

    public static RandomFeedbackListResponseDto of(RandomInterview randomInterview, List<RandomFeedback> feedbacks) {
        return RandomFeedbackListResponseDto.builder()
                .interviewId(randomInterview.getId())
                .title(randomInterview.getTitle())
                .feedbackList(feedbacks.stream().map(RandomFeedbackDetailResponseDto::of).toList())
                .build();
    }
}
