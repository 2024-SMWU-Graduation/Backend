package smwu.project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smwu.project.domain.entity.Interview;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
}
