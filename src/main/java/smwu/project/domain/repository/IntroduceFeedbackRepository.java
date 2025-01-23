package smwu.project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smwu.project.domain.entity.IntroduceFeedback;
import smwu.project.global.exception.CustomException;

public interface IntroduceFeedbackRepository extends JpaRepository<IntroduceFeedback, Long> {
}
