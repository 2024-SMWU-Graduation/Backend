package smwu.project.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import smwu.project.domain.entity.IntroduceInterview;
import smwu.project.domain.enums.InterviewStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class IntroduceInterviewResponseDto {
    Long interviewId;
    String title;
    String videoUrl;
    InterviewStatus interviewStatus;
    LocalDateTime createdAt;

    public static IntroduceInterviewResponseDto of(IntroduceInterview interview) {
        return IntroduceInterviewResponseDto.builder()
                .interviewId(interview.getId())
                .title(interview.getTitle())
                .videoUrl(interview.getVideoUrl())
                .interviewStatus(interview.getInterviewStatus())
                .createdAt(interview.getCreatedAt())
                .build();
    }
}

