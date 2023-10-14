package dwu.swcmop.trippacks.repository;

import dwu.swcmop.trippacks.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    User save(User user);
    // JPA findBy 규칙
    // select * from user_master where kakao_email = ?
    User findByKakaoEmail(String kakaoEmail);

   User findByUserCode(Long userCode);

    User findByKakaoId(Long kakaoId);
    Optional<User> findById(Long userCode);
}