package pp3.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pp3.security.module.User;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
