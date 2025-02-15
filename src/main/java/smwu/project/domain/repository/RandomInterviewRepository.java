package smwu.project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smwu.project.domain.entity.RandomInterview;
import smwu.project.global.exception.CustomException;
import smwu.project.global.exception.errorCode.InterviewErrorCode;

public interface RandomInterviewRepository extends JpaRepository<RandomInterview, Long> {
    default RandomInterview findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new CustomException(InterviewErrorCode.INTERVIEW_NOT_FOUND));
    }

}
