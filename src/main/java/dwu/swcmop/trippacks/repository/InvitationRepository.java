package dwu.swcmop.trippacks.repository;

import dwu.swcmop.trippacks.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InvitationRepository  extends JpaRepository<Invitation, Integer> {
    @Query("SELECT i FROM Invitation i WHERE i.slug = :slug")
    Optional<Invitation> findOneBySlug(@Param("slug") String slug);

    @Query("SELECT i FROM Invitation i WHERE i.bag.id = :bagId")
    Optional<Invitation> findOneByBagId(@Param("bagId")Long bagId);
}
