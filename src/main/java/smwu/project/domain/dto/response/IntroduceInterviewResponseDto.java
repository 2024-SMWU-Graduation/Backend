package smwu.project.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import smwu.project.domain.entity.IntroduceInterview;

import java.time.LocalDateTime;

@Getter
@Builder
public class IntroduceInterviewResponseDto {
    Long interviewId;
    String title;
    String videoUrl;
    LocalDateTime createdAt;

    public static IntroduceInterviewResponseDto of(IntroduceInterview interview) {
        return IntroduceInterviewResponseDto.builder()
                .interviewId(interview.getId())
                .title(interview.getTitle())
                .videoUrl(interview.getVideoUrl())
                .createdAt(interview.getCreatedAt())
                .build();
    }
}

