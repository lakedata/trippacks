package dwu.swcmop.trippacks.service;

import dwu.swcmop.trippacks.dto.BagRequest;
import dwu.swcmop.trippacks.dto.BagStatus;
import dwu.swcmop.trippacks.entity.Bag;
import dwu.swcmop.trippacks.entity.User;
import dwu.swcmop.trippacks.exception.ResourceNotFoundException;
import dwu.swcmop.trippacks.repository.BagRepository;
import dwu.swcmop.trippacks.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BagService {
    private final BagRepository bagRepository;
    private final UserRepository userRepository;

    //가방 생성
    @Transactional
    public Bag createBag(Long kakaoId, BagRequest request) {
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
    //마감순
    @Transactional
    public Page<Bag> findAllBag(Long kakaoId, BagStatus status, Pageable pageable) {
        List<Bag> allBags = bagRepository.findByKakaoIdAndStatus(kakaoId, status);

        Pageable paging = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort()
        );

        List<Bag> pagedBags = allBags.stream()
                .sorted((bag1, bag2) -> {
                    try {
                        Date startDate1 = null;
                        Date startDate2 = null;

                        if (isValidDate(bag1.getStartDate())) {
                            startDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(bag1.getStartDate());
                        }

                        if (isValidDate(bag2.getStartDate())) {
                            startDate2 = new SimpleDateFormat("yyyy-MM-dd").parse(bag2.getStartDate());
                        }

                        int startDateComparison = 0;

                        if (startDate1 != null && startDate2 != null) {
                            startDateComparison = startDate1.compareTo(startDate2);
                        } else if (startDate1 != null) {
                            return -1; // startDate2가 유효하지 않으면 startDate1을 앞으로 이동
                        } else if (startDate2 != null) {
                            return 1; // startDate1이 유효하지 않으면 startDate2를 앞으로 이동
                        }

                        Date endDate1 = null;
                        Date endDate2 = null;

                        if (isValidDate(bag1.getEndDate())) {
                            endDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(bag1.getEndDate());
                        }

                        if (isValidDate(bag2.getEndDate())) {
                            endDate2 = new SimpleDateFormat("yyyy-MM-dd").parse(bag2.getEndDate());
                        }

                        int endDateComparison = 0;

                        if (endDate1 != null && endDate2 != null) {
                            endDateComparison = endDate1.compareTo(endDate2);
                        } else if (endDate1 != null) {
                            return -1; // endDate2가 유효하지 않으면 endDate1을 앞으로 이동
                        } else if (endDate2 != null) {
                            return 1; // endDate1이 유효하지 않으면 endDate2를 앞으로 이동
                        }

                        if (startDateComparison == 0) {
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
                        e.printStackTrace();
                        return 0;
                    }
                })
                .skip(paging.getOffset())
                .limit(paging.getPageSize())
                .collect(Collectors.toList());

        return new PageImpl<>(pagedBags, paging, allBags.size());
    }

    // 날짜 유효성을 검사하는 메서드 추가
    private boolean isValidDate(String date) {
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    //등록순
    @Transactional
    public Page<Bag> findAllPagedLatestBag(Long kakaoId, BagStatus status, Pageable pageable) {
        List<Bag> bags = bagRepository.findByKakaoIdAndStatus(kakaoId, status);
        Collections.reverse(bags);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), bags.size());

        return new PageImpl<>(bags.subList(start, end), pageable, bags.size());
    }

    @Transactional
    public Bag findBag(Long bagId) {
        return bagRepository.findByBagId(bagId);
    }

    //여행정보에 사용
    public List<Bag> findAll(Long kakaoId) {
        List<Bag> bags = bagRepository.findAllByKakaoId(kakaoId);
        return bags;
    }

    //가방 수정
    @Transactional
    public Bag updateBag(Long id, BagRequest request) {
        Bag bag = bagRepository.findByBagId(id);
        if (bag == null) new ResourceNotFoundException("Bag", "id", id);
        bag.setBagName(request.getBagName());
        bag.setLocation(request.getLocation());
        bag.setStartDate(request.getStartDate());
        bag.setEndDate(request.getEndDate());
        return bagRepository.save(bag);
    }

    //가방 삭제
    @Transactional
    public void deleteBag(Long id) {
        Bag bag = bagRepository.findByBagId(id);
        if (bag == null) new ResourceNotFoundException("Bag", "id", id);
        bagRepository.delete(bag);
    }

    //가방 종료
    @Transactional
    public Bag closeBag(Long id) {
        Bag bag = bagRepository.findByBagId(id);
        if (bag == null) new ResourceNotFoundException("Bag", "id", id);
        bag.setStatus(BagStatus.FINISHED);
        return bag;
    }

    //가방 오픈
    @Transactional
    public Bag openBag(Long id) {
        Bag bag = bagRepository.findByBagId(id);
        if (bag == null) new ResourceNotFoundException("Bag", "id", id);
        bag.setStatus(BagStatus.AVAILABLE);
        return bag;
    }
/*
    @Transactional
    public List<Bag> findClosedBagsByKakaoId(Long kakaoId) {
        List<Bag> closeBags = bagRepository.findAllByKakaoIdAndStatus(kakaoId, BagStatus.FINISHED);
        closeBags.sort(Comparator.comparing(bag -> {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(bag.getStartDate());
            } catch (ParseException e) {
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
                e.printStackTrace();
                return null;
            }
        }));

        return openBags;
    }
*/
}