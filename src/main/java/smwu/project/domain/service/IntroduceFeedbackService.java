package smwu.project.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu.project.domain.dto.request.IntroduceAnalyzeUpdateRequestDto;
import smwu.project.domain.dto.request.IntroduceFeedbackRequestDto;
import smwu.project.domain.dto.request.FeedbackTimelineRequestDto;
import smwu.project.domain.dto.response.FeedbackResponseDto;
import smwu.project.domain.entity.FeedbackTimeline;
import smwu.project.domain.entity.IntroduceFeedback;
import smwu.project.domain.entity.IntroduceInterview;
import smwu.project.domain.entity.User;
import smwu.project.domain.repository.IntroduceFeedbackRepository;
import smwu.project.domain.repository.IntroduceInterviewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IntroduceFeedbackService {
    private final IntroduceInterviewRepository introduceInterviewRepository;
    private final IntroduceFeedbackRepository introduceFeedbackRepository;

    @Transactional
    public void saveInterviewFeedback(User user, IntroduceFeedbackRequestDto requestDto) {
        IntroduceInterview interview = introduceInterviewRepository.findByUserAndIdOrElseThrow(user, requestDto.getInterviewId());

        List<FeedbackTimeline> timelines = requestDto.getTimelines().stream()
                .map(FeedbackTimelineRequestDto::toEntity)
                .toList();

        IntroduceFeedback introduceFeedback = new IntroduceFeedback(
                interview,
                requestDto.getNegativePercentage(),
                timelines
        );
        introduceFeedbackRepository.save(introduceFeedback);
    }

    public FeedbackResponseDto readFeedback(User user, Long interviewId) {
        IntroduceInterview interview = introduceInterviewRepository.findByUserAndIdOrElseThrow(user, interviewId);
        IntroduceFeedback feedback = introduceFeedbackRepository.findByInterviewOrElseThrow(interview);
        return FeedbackResponseDto.of(feedback, interview.getVideoUrl());
    }

    @Transactional
    public void updateAnalyzeLink(IntroduceAnalyzeUpdateRequestDto requestDto) {
        IntroduceInterview interview = introduceInterviewRepository.findByIdOrElseThrow(requestDto.getInterviewId());
        IntroduceFeedback feedback = introduceFeedbackRepository.findByInterviewOrElseThrow(interview);
        feedback.setAnalyzeUrl(requestDto.getAnalyzeLink());
        interview.updateInterviewStatusToCompleted();
    }
}
