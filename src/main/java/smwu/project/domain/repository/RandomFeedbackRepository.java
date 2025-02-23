package smwu.project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smwu.project.domain.entity.RandomFeedback;
import smwu.project.domain.entity.RandomQuestion;
import smwu.project.global.exception.CustomException;
import smwu.project.global.exception.errorCode.FeedbackErrorCode;

import java.util.List;
import java.util.Optional;

public interface RandomFeedbackRepository extends JpaRepository<RandomFeedback, Long> {
    Optional<RandomFeedback> findByRandomQuestion(RandomQuestion randomQuestion);
    default RandomFeedback findByRandomQuestionOrElseThrow(RandomQuestion randomQuestion) {
        return findByRandomQuestion(randomQuestion).orElseThrow(() ->
                new CustomException(FeedbackErrorCode.FEEDBACK_NOT_FOUND));
    }
}
