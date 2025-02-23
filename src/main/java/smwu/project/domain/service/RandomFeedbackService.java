package smwu.project.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smwu.project.domain.dto.request.FeedbackTimelineRequestDto;
import smwu.project.domain.dto.request.RandomAnalyzeUpdateRequestDto;
import smwu.project.domain.dto.request.RandomFeedbackRequestDto;
import smwu.project.domain.entity.FeedbackTimeline;
import smwu.project.domain.entity.RandomFeedback;
import smwu.project.domain.entity.RandomQuestion;
import smwu.project.domain.entity.User;
import smwu.project.domain.repository.RandomFeedbackRepository;
import smwu.project.domain.repository.RandomQuestionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RandomFeedbackService {
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

//        RandomFeedback randomFeedback = RandomFeedback.builder()
//                .randomQuestion(randomQuestion)
//                .negativePercentage(requestDto.getNegativePercentage())
//                .timelines(requestDto.getTimelines().toString())
//                .build();

        randomFeedbackRepository.save(randomFeedback);
    }

    @Transactional
    public void updateAnalyzeLink(RandomAnalyzeUpdateRequestDto requestDto) {
        RandomQuestion randomQuestion = randomQuestionRepository.findByIdOrElseThrow(requestDto.getQuestionId());
        RandomFeedback randomFeedback = randomFeedbackRepository.findByRandomQuestionOrElseThrow(randomQuestion);
        randomFeedback.setAnalyzeUrl(requestDto.getAnalyzeLink());
    }
}
