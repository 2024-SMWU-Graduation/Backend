package smwu.project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smwu.project.domain.entity.IntroduceInterview;
import smwu.project.domain.entity.User;
import smwu.project.domain.enums.InterviewStatus;
import smwu.project.global.exception.CustomException;
import smwu.project.global.exception.errorCode.InterviewErrorCode;

import java.util.List;
import java.util.Optional;

public interface IntroduceInterviewRepository extends JpaRepository<IntroduceInterview, Long> {
    Optional<IntroduceInterview> findByUserAndId(User user, Long id);
    List<IntroduceInterview> findAllByUserAndInterviewStatusNot(User user, InterviewStatus interviewStatus);

    default IntroduceInterview findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(()
        -> new CustomException(InterviewErrorCode.INTERVIEW_NOT_FOUND));
    }

    default IntroduceInterview findByUserAndIdOrElseThrow(User user, Long id) {
        return findByUserAndId(user, id).orElseThrow(()
                -> new CustomException(InterviewErrorCode.INTERVIEW_NOT_FOUND));
    }
}
