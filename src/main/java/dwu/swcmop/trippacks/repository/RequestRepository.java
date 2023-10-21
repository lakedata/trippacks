package dwu.swcmop.trippacks.repository;

import dwu.swcmop.trippacks.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByToFriendId(Long toFriendId);
    List<Request> findByFromUserIdAndToFriendIdOrFromUserIdAndToFriendId(Long fromUserId1, Long toFriendId1, Long fromUserId2, Long toFriendId2);

    @Transactional
    void deleteByFromUserIdOrToFriendId(Long fromUserId, Long toFriendId);
}
