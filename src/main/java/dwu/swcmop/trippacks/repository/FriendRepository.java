package dwu.swcmop.trippacks.repository;
import dwu.swcmop.trippacks.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    Friend findUserEntityByUserIdAndFriendId(Long userId, Long friendId);

    List<Friend> findUserEntityByUserId(Long userId);

}
