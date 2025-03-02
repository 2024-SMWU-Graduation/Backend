package smwu.project.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu.project.domain.dto.request.EditTitleRequestDto;
import smwu.project.domain.dto.response.CreateRandomInterviewResponseDto;
import smwu.project.domain.dto.response.RandomInterviewListResponseDto;
import smwu.project.domain.entity.RandomInterview;
import smwu.project.domain.entity.User;
import smwu.project.domain.enums.InterviewStatus;
import smwu.project.domain.repository.RandomInterviewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RandomInterviewService {
    private final RandomInterviewRepository randomInterviewRepository;
    private final static String RANDOM_INTERVIEW_TITLE = "직무별 꼬리 질문 면접";

    @Transactional
    public CreateRandomInterviewResponseDto createRandomInterview(User user) {
        RandomInterview randomInterview = RandomInterview.builder()
                .user(user)
                .title(RANDOM_INTERVIEW_TITLE)
                .interviewStatus(InterviewStatus.TEMP)
                .build();

        RandomInterview savedInterview = randomInterviewRepository.save(randomInterview);

        return CreateRandomInterviewResponseDto.of(savedInterview);
    }

    public RandomInterviewListResponseDto readRandomInterviewList(User user) {
        List<RandomInterview> interviewList = randomInterviewRepository.findAllByUser(user);
        return RandomInterviewListResponseDto.of(interviewList);
    }

    @Transactional
    public void editInterviewTitle(User user, EditTitleRequestDto requestDto) {
        Long interviewId = requestDto.getInterviewId();
        RandomInterview randomInterview = randomInterviewRepository.findByUserAndIdOrElseThrow(user, interviewId);
        randomInterview.setTitle(requestDto.getTitle());
    }

    @Transactional
    public void deleteInterview(User user, Long interviewId) {
        RandomInterview randomInterview = randomInterviewRepository.findByUserAndIdOrElseThrow(user, interviewId);
        randomInterviewRepository.delete(randomInterview);
    }
}
