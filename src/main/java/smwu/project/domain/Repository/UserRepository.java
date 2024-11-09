package smwu.project.domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smwu.project.domain.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
