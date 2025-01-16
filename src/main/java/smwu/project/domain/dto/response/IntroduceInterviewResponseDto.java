package smwu.project.domain.dto.response;

import lombok.Getter;
import smwu.project.domain.entity.IntroduceInterview;

import java.time.LocalDateTime;

@Getter
public class IntroduceInterviewResponseDto {
    Long interviewId;
    String title;
    String videoUrl;
    LocalDateTime createdAt;

    public IntroduceInterviewResponseDto(IntroduceInterview interview) {
        this.interviewId = interview.getId();
        this.title = interview.getTitle();
        this.videoUrl = interview.getVideoUrl();
        this.createdAt = interview.getCreatedAt();
    }
}

