package dwu.swcmop.trippacks.service;

import dwu.swcmop.trippacks.entity.Bag;
import dwu.swcmop.trippacks.repository.BagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class BagService {
    private final BagRepository bagRepository;


//    public Bag createBag(Bag bag, Long userCode){
//
//        Bag newBag = new Bag();
//    }
}
