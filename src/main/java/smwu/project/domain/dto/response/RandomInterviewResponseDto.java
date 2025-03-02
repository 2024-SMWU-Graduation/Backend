package smwu.project.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import smwu.project.domain.entity.RandomInterview;
import smwu.project.domain.entity.RandomQuestion;

import java.time.LocalDateTime;

@Getter
@Builder
public class RandomInterviewResponseDto {
    Long interviewId;
    String title;
    String videoUrl;
    LocalDateTime createdAt;

    public static RandomInterviewResponseDto of(RandomInterview interview) {
        RandomQuestion firstQuestion = interview.getRandomQuestions().get(0);
        String videoUrl = firstQuestion != null ? firstQuestion.getVideoUrl() : null;

        return RandomInterviewResponseDto.builder()
                .interviewId(interview.getId())
                .title(interview.getTitle())
                .videoUrl(videoUrl)
                .createdAt(interview.getCreatedAt())
                .build();
    }
}