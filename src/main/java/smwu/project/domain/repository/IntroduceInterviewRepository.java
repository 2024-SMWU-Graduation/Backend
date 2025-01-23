package smwu.project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smwu.project.domain.entity.IntroduceInterview;
import smwu.project.domain.entity.User;
import smwu.project.global.exception.CustomException;
import smwu.project.global.exception.errorCode.InterviewErrorCode;

import java.util.List;
import java.util.Optional;

public interface IntroduceInterviewRepository extends JpaRepository<IntroduceInterview, Long> {
    List<IntroduceInterview> findAllByUser(User user);
    Optional<IntroduceInterview> findIntroduceInterviewByIdAndUser(Long id, User user);

    default IntroduceInterview findByIdAndUserOrElseThrow(Long id, User user) {
        return findIntroduceInterviewByIdAndUser(id, user).orElseThrow(() -> new CustomException(InterviewErrorCode.INTERVIEW_NOT_FOUND));
    }
}
