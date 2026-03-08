package smwu.project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smwu.project.domain.entity.IntroduceFeedback;
import smwu.project.domain.entity.IntroduceInterview;
import smwu.project.global.exception.CustomException;
import smwu.project.global.exception.errorCode.FeedbackErrorCode;

import java.util.Optional;

public interface IntroduceFeedbackRepository extends JpaRepository<IntroduceFeedback, Long> {
    Optional<IntroduceFeedback> findByIntroduceInterview(IntroduceInterview introduceInterview);

    default IntroduceFeedback findByInterviewOrElseThrow(IntroduceInterview interview) {
        return findByIntroduceInterview(interview).orElseThrow(()
                -> new CustomException(FeedbackErrorCode.FEEDBACK_NOT_FOUND));
    }
}
