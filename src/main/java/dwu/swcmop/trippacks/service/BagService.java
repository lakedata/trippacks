package dwu.swcmop.trippacks.service;

import dwu.swcmop.trippacks.dto.BagRequest;
import dwu.swcmop.trippacks.dto.BagStatus;
import dwu.swcmop.trippacks.entity.Bag;
import dwu.swcmop.trippacks.entity.Pack;
import dwu.swcmop.trippacks.entity.User;
import dwu.swcmop.trippacks.exception.ResourceNotFoundException;
import dwu.swcmop.trippacks.repository.BagRepository;
import dwu.swcmop.trippacks.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class BagService {
    private final BagRepository bagRepository;
    private final UserRepository userRepository;

    //가방 생성
    @Transactional
    public Bag createBag(Long userCode, BagRequest request){
        User user = userRepository.findByUserCode(userCode);
        Bag newBag = Bag.builder()
                .user(user)
                .location(request.getLocation())
                .bagName(request.getBagName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
        newBag.setStatus(BagStatus.AVAILABLE);
        return bagRepository.save(newBag);
    }

    //가방 조회
    @Transactional
    public List<Bag> findAllBag(Long userCode){
        User user = userRepository.findByUserCode(userCode);
        return bagRepository.findAllByUser(user);
    }

    @Transactional
    public Bag findBag(Long bagId){
        return bagRepository.findByBagId(bagId);
    }

    //가방 수정
    @Transactional
    public Bag updateBag(Long id, BagRequest request){
        Bag bag = bagRepository.findByBagId(id);
        if(bag == null) new ResourceNotFoundException("Bag", "id", id);
        bag.setBagName(request.getBagName());
        bag.setLocation(request.getLocation());
        bag.setStartDate(request.getStartDate());
        bag.setEndDate(request.getEndDate());
        return bagRepository.save(bag);
    }

    //가방 삭제
    @Transactional
    public void deleteBag(Long id){
        Bag bag = bagRepository.findByBagId(id);
        if(bag == null) new ResourceNotFoundException("Bag", "id", id);
        bagRepository.delete(bag);
    }

    //가방 종료
    @Transactional
    public Bag closeBag(Long id){
        Bag bag = bagRepository.findByBagId(id);
        if(bag == null) new ResourceNotFoundException("Bag", "id", id);
        bag.setStatus(BagStatus.FINISHED);
        return bag;
    }

}