package smwu.project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smwu.project.domain.entity.RandomQuestion;

public interface RandomQuestionRepository extends JpaRepository<RandomQuestion, Long> {
}
