package smwu.project.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import smwu.project.domain.dto.request.CreateRandomQuestionRequestDto;
import smwu.project.domain.dto.request.TailQuestionRequestDto;
import smwu.project.domain.dto.response.RandomQuestionResponseDto;
import smwu.project.domain.dto.response.TailQuestionResponseDto;
import smwu.project.domain.entity.RandomInterview;
import smwu.project.domain.entity.RandomQuestion;
import smwu.project.domain.entity.User;
import smwu.project.domain.repository.RandomInterviewRepository;
import smwu.project.domain.repository.RandomQuestionRepository;
import smwu.project.global.util.S3Uploader;

import java.util.Comparator;
import java.util.List;

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

    // 만약 꼬리질문을 생성할 때 해당 인터뷰에 함께 생성된 꼬리질문이 이미 존재한다면, 저장하지 않도록 설정
    @Transactional
    public void createTailQuestion(TailQuestionRequestDto requestDto) {
        Long interviewId = requestDto.getInterviewId();
        RandomInterview randomInterview = randomInterviewRepository.findByIdOrElseThrow(interviewId);

//        List<RandomQuestion> questionList = randomQuestionRepository.findAllByRandomInterview(randomInterview);
//
//        if(questionList.size() > 3) {
//            return false;
//        }

        RandomQuestion randomQuestion = RandomQuestion.builder()
                .randomInterview(randomInterview)
                .questionData(requestDto.getQuestionData())
                .build();

        randomQuestionRepository.save(randomQuestion);
//        return true;
    }

    @Transactional
    public RandomQuestionResponseDto uploadTailQuestion(User user, Long questionId, MultipartFile file) {
        RandomQuestion randomQuestion = randomQuestionRepository.findByIdOrElseThrow(questionId);

        String videoUrl = s3Uploader.uploadRandomQuestion(file, user.getId(), randomQuestion.getRandomInterview().getId(), randomQuestion.getId(), randomQuestion.getQuestionData());
        randomQuestion.setVideoUrl(videoUrl);
        return RandomQuestionResponseDto.of(randomQuestion);
    }

    public TailQuestionResponseDto getTailQuestion(Long interviewId) {
        RandomInterview randomInterview = randomInterviewRepository.findByIdOrElseThrow(interviewId);
        List<RandomQuestion> questionList = randomQuestionRepository.findAllByRandomInterview(randomInterview);

        // 처음 질문만 생성된 상황일 때
        if(questionList.size() == 1) {
            return TailQuestionResponseDto.of();
        } else {
            RandomQuestion question = questionList.stream()
                    .max(Comparator.comparing(RandomQuestion::getCreatedAt))
                    .orElse(null);

            return TailQuestionResponseDto.of(question);
        }
    }
}
