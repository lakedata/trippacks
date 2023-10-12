package dwu.swcmop.trippacks.repository;

import dwu.swcmop.trippacks.entity.Bag;
import dwu.swcmop.trippacks.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BagRepository extends JpaRepository<Bag, Long> {
    public List<Bag> findAllByUser(User user);
    public Bag findByBagId(Long id);
}
