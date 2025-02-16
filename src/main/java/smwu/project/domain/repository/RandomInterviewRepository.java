package smwu.project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smwu.project.domain.entity.RandomInterview;
import smwu.project.domain.entity.User;
import smwu.project.global.exception.CustomException;
import smwu.project.global.exception.errorCode.InterviewErrorCode;

import java.util.List;

public interface RandomInterviewRepository extends JpaRepository<RandomInterview, Long> {
    List<RandomInterview> findAllByUser(User user);
    default RandomInterview findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new CustomException(InterviewErrorCode.INTERVIEW_NOT_FOUND));
    }

}
