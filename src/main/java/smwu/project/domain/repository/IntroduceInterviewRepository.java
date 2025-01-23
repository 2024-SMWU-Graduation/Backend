package smwu.project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smwu.project.domain.entity.IntroduceInterview;
import smwu.project.domain.entity.User;
import smwu.project.global.exception.CustomException;
import smwu.project.global.exception.errorCode.IntroduceInterviewErrorCode;

import java.util.List;
import java.util.Optional;

public interface IntroduceInterviewRepository extends JpaRepository<IntroduceInterview, Long> {
    Optional<IntroduceInterview> findByUserAndId(User user, Long id);
    List<IntroduceInterview> findAllByUser(User user);

    default IntroduceInterview findByIdOrElseThrow(User user, Long id) {
        return findByUserAndId(user, id).orElseThrow(() ->
                new CustomException(IntroduceInterviewErrorCode.NOT_FOUND));
    }
}
