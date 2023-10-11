package dwu.swcmop.trippacks.repository;

import dwu.swcmop.trippacks.entity.Bag;
import dwu.swcmop.trippacks.entity.Pack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PackRepository extends JpaRepository<Pack, Long> {
    public List<Pack> findPackByBag(Bag bag);

}
