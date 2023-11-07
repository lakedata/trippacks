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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import org.springframework.data.domain.Sort;


@Service
@AllArgsConstructor
public class BagService {
    private final BagRepository bagRepository;
    private final UserRepository userRepository;

    //가방 생성
    @Transactional
    public Bag createBag(Long kakaoId, BagRequest request){
        User user = userRepository.findByKakaoId(kakaoId); //변경
        System.out.println(user);
        Bag newBag = Bag.builder()
                .user(user)
                .location(request.getLocation())
                .bagName(request.getBagName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .kakaoId(kakaoId)
                .build();
        newBag.setStatus(BagStatus.AVAILABLE);
        return bagRepository.save(newBag);
    }

    //가방 조회
    @Transactional
    public List<Bag> findAllBag(Long kakaoId) {
        List<Bag> allBags = bagRepository.findAllByKakaoId(kakaoId);

        allBags.sort((bag1, bag2) -> {
            try {
                Date startDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(bag1.getStartDate());
                Date startDate2 = new SimpleDateFormat("yyyy-MM-dd").parse(bag2.getStartDate());

                int startDateComparison = startDate1.compareTo(startDate2);

                if (startDateComparison == 0) {
                    Date endDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(bag1.getEndDate());
                    Date endDate2 = new SimpleDateFormat("yyyy-MM-dd").parse(bag2.getEndDate());

                    int endDateComparison = endDate1.compareTo(endDate2);

                    if (endDateComparison == 0) {
                        // 시작 날짜와 종료 날짜가 같은 경우, bagId를 기준으로 정렬
                        return bag1.getBagId().compareTo(bag2.getBagId());
                    } else {
                        // 시작 날짜가 같고 종료 날짜가 다른 경우 종료 날짜를 기준으로 정렬
                        return endDateComparison;
                    }
                }

                return startDateComparison;
            } catch (ParseException e) {
                // 파싱 예외를 처리합니다.
                e.printStackTrace();
                return 0;
            }
        });

        return allBags;
    }
    public List<Bag> findAlllatestBag(Long kakaoId){
        List<Bag> bags = bagRepository.findAllByKakaoId(kakaoId);
        Collections.reverse(bags); // Reverse the list

        return bags;
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

    //가방 오픈
    @Transactional
    public Bag openBag(Long id){
        Bag bag = bagRepository.findByBagId(id);
        if(bag == null) new ResourceNotFoundException("Bag", "id", id);
        bag.setStatus(BagStatus.AVAILABLE);
        return bag;
    }

    @Transactional
    public List<Bag> findClosedBagsByKakaoId(Long kakaoId) {
        List<Bag> closeBags = bagRepository.findAllByKakaoIdAndStatus(kakaoId, BagStatus.FINISHED);
        closeBags.sort(Comparator.comparing(bag -> {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(bag.getStartDate());
            } catch (ParseException e) {
                // 파싱 예외를 처리합니다.
                e.printStackTrace();
                return null;
            }
        }));

        return closeBags;
    }

    @Transactional
    public List<Bag> findOpenBagsByKakaoId(Long kakaoId) {
        List<Bag> openBags = bagRepository.findAllByKakaoIdAndStatus(kakaoId, BagStatus.AVAILABLE);

        // startDate를 기준으로 가방을 정렬합니다.
        openBags.sort(Comparator.comparing(bag -> {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(bag.getStartDate());
            } catch (ParseException e) {
                // 파싱 예외를 처리합니다.
                e.printStackTrace();
                return null;
            }
        }));

        return openBags;
    }
}