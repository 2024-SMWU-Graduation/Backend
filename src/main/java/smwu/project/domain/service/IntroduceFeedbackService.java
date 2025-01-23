package smwu.project.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu.project.domain.dto.request.FeedbackRequestDto;
import smwu.project.domain.dto.response.FeedbackResponseDto;
import smwu.project.domain.entity.IntroduceFeedback;
import smwu.project.domain.entity.IntroduceInterview;
import smwu.project.domain.entity.User;
import smwu.project.domain.repository.IntroduceFeedbackRepository;
import smwu.project.domain.repository.IntroduceInterviewRepository;

@Service
@RequiredArgsConstructor
public class IntroduceFeedbackService {
    private final IntroduceInterviewRepository introduceInterviewRepository;
    private final IntroduceFeedbackRepository introduceFeedbackRepository;

    @Transactional
    public void saveInterviewFeedback(User user, FeedbackRequestDto requestDto) {
        IntroduceInterview introduceInterview = introduceInterviewRepository.findByIdOrElseThrow(user, requestDto.getInterviewId());

        IntroduceFeedback introduceFeedback = IntroduceFeedback.builder()
                .introduceInterview(introduceInterview)
                .negativePercentage(requestDto.getPercentage())
                .timelines(requestDto.getTimelines().toString())
                .build();

        introduceFeedbackRepository.save(introduceFeedback);
    }

    public FeedbackResponseDto readFeedback(User user, Long interviewId) {
        IntroduceInterview interview = introduceInterviewRepository.findByIdOrElseThrow(user, interviewId);
        IntroduceFeedback feedback = introduceFeedbackRepository.findByInterviewOrElseThrow(interview);
        return FeedbackResponseDto.of(feedback, interview);
    }
}
