package dwu.swcmop.trippacks.repository;

import dwu.swcmop.trippacks.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByKakaoEmail(String kakaoEmail);

    User findByUserCode(Long userCode);

    User findByKakaoId(Long kakaoId);

    User findByKakaoNickname(String kakaoNickname);

    Optional<User> findById(Long userCode);

    List<User> findByUserCodeIn(List<Long> userCodes);
}