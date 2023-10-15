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

    // 사용자 ID에 해당하는 친구 목록을 반환하는 메서드
    List<Friend> findByUserIdAndIsFriend(Long userId, Boolean isFriend);

    // 친구 ID에 해당하는 친구 목록을 반환하는 메서드
    List<Friend> findByFriendIdAndIsFriend(Long friendId, Boolean isFriend);

    void deleteAllByUserIdOrFriendId(Long userId, Long friendId);

}
