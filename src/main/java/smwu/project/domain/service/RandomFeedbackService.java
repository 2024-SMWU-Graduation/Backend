package smwu.project.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu.project.domain.dto.request.FeedbackTimelineRequestDto;
import smwu.project.domain.dto.request.RandomAnalyzeUpdateRequestDto;
import smwu.project.domain.dto.request.RandomFeedbackRequestDto;
import smwu.project.domain.dto.response.RandomFeedbackListResponseDto;
import smwu.project.domain.entity.*;
import smwu.project.domain.repository.RandomFeedbackRepository;
import smwu.project.domain.repository.RandomInterviewRepository;
import smwu.project.domain.repository.RandomQuestionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RandomFeedbackService {
    private final RandomInterviewRepository randomInterviewRepository;
    private final RandomFeedbackRepository randomFeedbackRepository;
    private final RandomQuestionRepository randomQuestionRepository;

    @Transactional
    public void saveQuestionFeedback(User user, RandomFeedbackRequestDto requestDto) {
        RandomQuestion randomQuestion = randomQuestionRepository.findByIdOrElseThrow(requestDto.getQuestionId());

        List<FeedbackTimeline> timelines = requestDto.getTimelines().stream()
                .map(FeedbackTimelineRequestDto::toEntity)
                .toList();

        RandomFeedback randomFeedback = new RandomFeedback(
                randomQuestion,
                requestDto.getNegativePercentage(),
                timelines
        );

        randomFeedbackRepository.save(randomFeedback);
    }

    @Transactional
    public void updateAnalyzeLink(RandomAnalyzeUpdateRequestDto requestDto) {
        RandomQuestion randomQuestion = randomQuestionRepository.findByIdOrElseThrow(requestDto.getQuestionId());
        RandomFeedback randomFeedback = randomFeedbackRepository.findByRandomQuestionOrElseThrow(randomQuestion);
        randomFeedback.setAnalyzeUrl(requestDto.getAnalyzeLink());
    }


    public RandomFeedbackListResponseDto readFeedbackList(User user, Long interviewId) {
        RandomInterview randomInterview = randomInterviewRepository.findByUserAndIdOrElseThrow(user, interviewId);

        List<RandomQuestion> questions = randomInterview.getRandomQuestions();
        List<RandomFeedback> feedbacks = new ArrayList<>();

        for(RandomQuestion question : questions) {
            RandomFeedback randomFeedback = randomFeedbackRepository.findByRandomQuestionOrElseThrow(question);
            feedbacks.add(randomFeedback);
        }

        return RandomFeedbackListResponseDto.of(randomInterview, feedbacks);
    }
}
