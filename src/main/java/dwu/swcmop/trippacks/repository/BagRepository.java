package dwu.swcmop.trippacks.repository;

import dwu.swcmop.trippacks.dto.BagStatus;
import dwu.swcmop.trippacks.entity.Bag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BagRepository extends JpaRepository<Bag, Long> {
    public List<Bag> findAllByKakaoId(Long kakaoId);

    public Bag findByBagId(Long id);

    List<Bag> findByKakaoIdAndStatus(Long kakaoId, BagStatus status);
}
