package dwu.swcmop.trippacks.repository;

import dwu.swcmop.trippacks.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);
}