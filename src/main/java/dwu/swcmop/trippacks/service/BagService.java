package dwu.swcmop.trippacks.service;

import dwu.swcmop.trippacks.config.BaseException;
import dwu.swcmop.trippacks.dto.BagRequest;
import dwu.swcmop.trippacks.dto.BagResponse;
import dwu.swcmop.trippacks.repository.BagRepository;
import dwu.swcmop.trippacks.entity.Bag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static dwu.swcmop.trippacks.config.BaseResponseStatus.SAVE_BAG_FAIL;

@Service
public class BagService {
    @Autowired
    private BagRepository bagRepository;
    public BagService(BagRepository bagRepository) {
        this.bagRepository = bagRepository;
    }

    // C - create
    public BagResponse createBag(BagRequest bag) throws BaseException {

        Bag saveBag = null;

        try {
            Bag newBag = new Bag(bag.getStatus(), bag.getBagName(), bag.getLocation(), bag.getStartDate(), bag.getEndDate());

            saveBag = bagRepository.save(newBag);
        } catch (Exception e) {
            throw new BaseException(SAVE_BAG_FAIL);
        }
        //BagResponse bagResponse = new BagResponse(saveBag.getBagId(), saveBag.getUser().getUserCode(), saveBag.getStatus(), saveBag.getBagName(), saveBag.getLocation(), saveBag.getStartDate(), saveBag.getEndDate());
        BagResponse bagResponse = new BagResponse(saveBag.getBagId(), saveBag.getStatus(), saveBag.getBagName(), saveBag.getLocation(), saveBag.getStartDate(), saveBag.getEndDate());

        return bagResponse;
    }
}
