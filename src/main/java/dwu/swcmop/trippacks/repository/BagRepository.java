package dwu.swcmop.trippacks.repository;

import dwu.swcmop.trippacks.entity.Bag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BagRepository extends JpaRepository<Bag, Long> {
//    Bag findByUserId(Long userId);
//
//    List<Bag> findAllByUserId(long userId);

//    Query(value = "SELECT * FROM Bags b WHERE b.user_id = :userID", nativeQuery = true)
//    public Bag findBagByUserID(@Param("userID") int userID);
}
