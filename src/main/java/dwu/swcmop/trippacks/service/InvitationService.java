package dwu.swcmop.trippacks.service;

import dwu.swcmop.trippacks.config.BaseException;
import dwu.swcmop.trippacks.config.BaseResponseStatus;
import dwu.swcmop.trippacks.entity.Bag;
import dwu.swcmop.trippacks.entity.Invitation;
import dwu.swcmop.trippacks.repository.BagRepository;
import dwu.swcmop.trippacks.repository.InvitationRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class InvitationService {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 8;

    private final InvitationRepository invitationRepository;
    private final BagRepository bagRepository;

    public InvitationService(InvitationRepository invitationRepository, BagRepository bagRepository) {
        this.invitationRepository = invitationRepository;
        this.bagRepository = bagRepository;
    }

    @Transactional(readOnly = true)
    public Invitation findOneBySlug(@Param("slug") String slug) throws BaseException {
        Invitation invitation = invitationRepository.findOneBySlug(slug).orElseThrow(() -> new BaseException(BaseResponseStatus.INVITATION_NOT_FOUND));
        LocalDateTime updatedAt = invitation.getUpdatedAt();

        if (updatedAt.isBefore(LocalDateTime.now().minusDays(1))) {
            throw new BaseException(BaseResponseStatus.INVITATION_EXPIRED);
        }
        return invitation;
    }

    @Transactional
    public Invitation createOrUpdate(@Param("bagId") Long bagId) throws BaseException {
        Bag bag = bagRepository.findById(bagId).orElseThrow(() ->
                new BaseException(BaseResponseStatus.BAG_NOT_FOUND));

        Optional<Invitation> invitation = invitationRepository.findOneByBagId(bagId);

        String slug = generateRandomString();
        Invitation newInvitation = invitation.orElseGet(() -> {
            Invitation invitation1 = new Invitation();
            invitation1.setCreatedAt(LocalDateTime.now());
            return invitation1;
        });

        newInvitation.setBag(bag);
        newInvitation.setSlug(slug);
        newInvitation.setUpdatedAt(LocalDateTime.now());

        return invitationRepository.save(newInvitation);
    }

    public static String generateRandomString() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(LENGTH);

        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

}
