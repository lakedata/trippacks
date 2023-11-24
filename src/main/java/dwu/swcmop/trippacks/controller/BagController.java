package dwu.swcmop.trippacks.controller;

import dwu.swcmop.trippacks.dto.BagRequest;
import dwu.swcmop.trippacks.dto.BagResponse;
import dwu.swcmop.trippacks.dto.BagStatus;
import dwu.swcmop.trippacks.dto.TripInfo;
import dwu.swcmop.trippacks.entity.Bag;
import dwu.swcmop.trippacks.service.BagService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
public class BagController {
    private BagService bagService;

    //가방 조회
    //마감순
    @GetMapping("/bag/list")
    public ResponseEntity<Page<Bag>> getBagList(
            @RequestParam Long kakaoId,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String status
    ) {
        BagStatus bagStatus = BagStatus.valueOf(status);
        Page<Bag> bagPage = bagService.findAllBag(kakaoId, bagStatus, PageRequest.of(page, size));
        return new ResponseEntity<>(bagPage, HttpStatus.OK);
    }

    //등록순
    @GetMapping("/bag/latestlist")
    public ResponseEntity<Page<Bag>> getBaglatestList(
            @RequestParam Long kakaoId,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String status

    ) {
        BagStatus bagStatus = BagStatus.valueOf(status);
        Page<Bag> bagPage = bagService.findAllPagedLatestBag(kakaoId, bagStatus, PageRequest.of(page, size));
        return new ResponseEntity<>(bagPage, HttpStatus.OK);
    }
/*
    @GetMapping("/closedBags")
    public List<Bag> getClosedBagsForKakaoId(@RequestParam Long kakaoId) {
        return bagService.findClosedBagsByKakaoId(kakaoId);
    }

    @GetMapping("/openBags")
    public List<Bag> getOpenBagsForKakaoId(@RequestParam Long kakaoId) {
        return bagService.findOpenBagsByKakaoId(kakaoId);
    }
*/
    @GetMapping("/bag/{id}")
    public BagResponse getBag(@PathVariable("id") Long id) {
        Bag bag = bagService.findBag(id);
        return BagResponse.fromEntity(bag);
    }

    @PostMapping("/bag/{kakaoId}")
    public Bag createBag(@RequestBody BagRequest request, @PathVariable("kakaoId") Long kakaoId) {
        return bagService.createBag(kakaoId, request);
    }

    @PutMapping("/bag/{id}")
    public Bag updateBag(@RequestBody BagRequest request, @PathVariable("id") Long id) {
        return bagService.updateBag(id, request);
    }

    @DeleteMapping("/bag/{id}")
    public void deleteBag(@PathVariable("id") Long id) {
        bagService.deleteBag(id);
    }

    @PutMapping("/bag/close/{id}")
    public Bag closeBag(@PathVariable("id") Long id) {
        return bagService.closeBag(id);
    }

    @PutMapping("/bag/open/{id}")
    public Bag openBag(@PathVariable("id") Long id) {
        return bagService.openBag(id);
    }

    //여행정보
    @GetMapping(value = "/bag/trip-info/{kakaoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TripInfo getTripInfo(@PathVariable("kakaoId") Long kakaoId) {
        List<Bag> bags = bagService.findAll(kakaoId);

        List<String> uniqueLocations = bags.stream()
                .map(Bag::getLocation)
                .distinct()
                .collect(Collectors.toList());

        int totalTripDuration = bags.stream()
                .mapToInt(bag -> calculateDuration(bag.getStartDate(), bag.getEndDate()))
                .sum();

        return new TripInfo(uniqueLocations.size(), totalTripDuration, uniqueLocations);
    }

    private int calculateDuration(String startDateString, String endDateString) {
        try {
            LocalDate startDate = LocalDate.parse(startDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate endDate = LocalDate.parse(endDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return (int) startDate.until(endDate, ChronoUnit.DAYS) + 1;
        } catch (DateTimeParseException e) {
            e.printStackTrace();// 날짜 형식이 잘못된 경우 0을 반환
            return 0;
        }
    }
}