package smwu.project.domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smwu.project.domain.entity.User;

public interface UserRepository extends JpaRepository<Long, User> {
}
