package smwu.project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smwu.project.domain.entity.RandomInterview;
import smwu.project.domain.entity.RandomQuestion;
import smwu.project.global.exception.CustomException;
import smwu.project.global.exception.errorCode.QuestionErrorCode;

import java.util.List;

public interface RandomQuestionRepository extends JpaRepository<RandomQuestion, Long> {
    List<RandomQuestion> findAllByRandomInterview(RandomInterview randomInterview);
    default RandomQuestion findByIdOrElseThrow(Long questionId) {
        return findById(questionId).orElseThrow(() ->
                new CustomException(QuestionErrorCode.QUESTION_NOT_FOUND));
    }

}
