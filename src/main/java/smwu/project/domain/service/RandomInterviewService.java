package smwu.project.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smwu.project.domain.dto.response.CreateRandomInterviewResponseDto;
import smwu.project.domain.entity.RandomInterview;
import smwu.project.domain.entity.User;
import smwu.project.domain.enums.InterviewStatus;
import smwu.project.domain.repository.RandomInterviewRepository;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RandomInterviewService {
    private final RandomInterviewRepository randomInterviewRepository;
    private final static String RANDOM_INTERVIEW_TITLE = "직무별 꼬리 질문 면접";

    public CreateRandomInterviewResponseDto createRandomInterview(User user) {
        RandomInterview randomInterview = RandomInterview.builder()
                .user(user)
                .title(RANDOM_INTERVIEW_TITLE)
                .interviewStatus(InterviewStatus.TEMP)
                .build();

        RandomInterview savedInterview = randomInterviewRepository.save(randomInterview);

        return CreateRandomInterviewResponseDto.of(savedInterview);
    }
}
