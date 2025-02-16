package smwu.project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smwu.project.domain.entity.IntroduceInterview;
import smwu.project.domain.entity.RandomInterview;
import smwu.project.domain.entity.User;
import smwu.project.global.exception.CustomException;
import smwu.project.global.exception.errorCode.InterviewErrorCode;

import java.util.List;
import java.util.Optional;

public interface RandomInterviewRepository extends JpaRepository<RandomInterview, Long> {
    Optional<RandomInterview> findByUserAndId(User user, Long interviewId);
    List<RandomInterview> findAllByUser(User user);

    default RandomInterview findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new CustomException(InterviewErrorCode.INTERVIEW_NOT_FOUND));
    }

    default RandomInterview findByUserAndIdOrElseThrow(User user, Long interviewId) {
        return findByUserAndId(user, interviewId).orElseThrow(()
                -> new CustomException(InterviewErrorCode.INTERVIEW_NOT_FOUND));
    }
}
