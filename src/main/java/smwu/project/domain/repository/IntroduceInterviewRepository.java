package smwu.project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smwu.project.domain.entity.IntroduceInterview;
import smwu.project.domain.entity.User;

import java.util.List;

public interface IntroduceInterviewRepository extends JpaRepository<IntroduceInterview, Long> {
    List<IntroduceInterview> findAllByUser(User user);
}
