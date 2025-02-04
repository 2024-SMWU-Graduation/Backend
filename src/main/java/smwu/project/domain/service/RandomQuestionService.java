package smwu.project.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import smwu.project.domain.dto.request.CreateRandomQuestionRequestDto;
import smwu.project.domain.dto.response.RandomQuestionResponseDto;
import smwu.project.domain.entity.RandomInterview;
import smwu.project.domain.entity.RandomQuestion;
import smwu.project.domain.entity.User;
import smwu.project.domain.repository.RandomInterviewRepository;
import smwu.project.domain.repository.RandomQuestionRepository;
import smwu.project.global.util.S3Uploader;

@Service
@RequiredArgsConstructor
public class RandomQuestionService {
    private final RandomQuestionRepository randomQuestionRepository;
    private final RandomInterviewRepository randomInterviewRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public RandomQuestionResponseDto uploadRandomQuestion(User user, MultipartFile file, CreateRandomQuestionRequestDto requestDto) {
        Long userId = user.getId();
        String questionData = requestDto.getQuestionData();

        RandomInterview randomInterview = randomInterviewRepository.findByIdOrElseThrow(requestDto.getInterviewId());

        RandomQuestion randomQuestion = RandomQuestion.builder()
                .randomInterview(randomInterview)
                .questionData(questionData)
                .build();

        randomQuestionRepository.save(randomQuestion);

        String videoUrl = s3Uploader.uploadRandomQuestion(file, userId, randomInterview.getId(), randomQuestion.getId(), questionData);
        randomQuestion.setVideoUrl(videoUrl);

        return RandomQuestionResponseDto.of(randomQuestion);
    }
}
