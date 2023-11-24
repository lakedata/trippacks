package dwu.swcmop.trippacks.repository;

import dwu.swcmop.trippacks.dto.BagStatus;
import dwu.swcmop.trippacks.entity.Bag;
import dwu.swcmop.trippacks.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BagRepository extends JpaRepository<Bag, Long> {
    public List<Bag> findAllByKakaoId(Long kakaoId);
    public Bag findByBagId(Long id);
    List<Bag> findByKakaoIdAndStatus(Long kakaoId, BagStatus status);
    //    List<Bag> findAllByKakaoIdAndStatus(Long kakaoId, BagStatus status);

}
