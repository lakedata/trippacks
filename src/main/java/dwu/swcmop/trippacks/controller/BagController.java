package dwu.swcmop.trippacks.controller;

import dwu.swcmop.trippacks.dto.BagRequest;
import dwu.swcmop.trippacks.dto.TripInfo;
import dwu.swcmop.trippacks.entity.Bag;
import dwu.swcmop.trippacks.service.BagService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
public class BagController {
    private BagService bagService;

    //가방 조회
    @GetMapping("/bag/list")
    public List<Bag> getBagList(@RequestParam Long kakaoId){
        return bagService.findAllBag(kakaoId);
    }

    @GetMapping("/bag/latestlist")
    public List<Bag> getBaglatestList(@RequestParam Long kakaoId){
        return bagService.findAlllatestBag(kakaoId);
    }

    @GetMapping("/closedBags")
    public List<Bag> getClosedBagsForKakaoId(@RequestParam Long kakaoId) {
        return bagService.findClosedBagsByKakaoId(kakaoId);
    }
    @GetMapping("/openBags")
    public List<Bag> getOpenBagsForKakaoId(@RequestParam Long kakaoId) {
        return bagService.findOpenBagsByKakaoId(kakaoId);
    }

    @GetMapping("/bag/{id}")
    public Bag getBag(@PathVariable("id") Long id){
        return bagService.findBag(id);
    }

    @PostMapping("/bag/{kakaoId}")
    public Bag createBag(@RequestBody BagRequest request, @PathVariable("kakaoId") Long kakaoId){
        return bagService.createBag(kakaoId, request);
    }

    @PutMapping("/bag/{id}")
    public Bag updateBag(@RequestBody BagRequest request, @PathVariable("id") Long id){
        return bagService.updateBag(id, request);
    }

    @DeleteMapping("/bag/{id}")
    public void deleteBag(@PathVariable("id") Long id){
        bagService.deleteBag(id);
    }

    @PutMapping("/bag/close/{id}")
    public Bag closeBag(@PathVariable("id") Long id){
        return bagService.closeBag(id);
    }

    @PutMapping("/bag/open/{id}")
    public Bag openBag(@PathVariable("id") Long id){
        return bagService.openBag(id);
    }

    //여행정보
    @GetMapping(value = "/bag/trip-info/{kakaoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TripInfo getTripInfo(@PathVariable("kakaoId") Long kakaoId) {
        List<Bag> bags = bagService.findAlllatestBag(kakaoId);

        List<String> uniqueLocations = bags.stream()
                .map(Bag::getLocation)
                .distinct()
                .collect(Collectors.toList());

        int totalTripDuration = (int) bags.stream()
                .mapToLong(bag -> {
                    LocalDate startDate = LocalDate.parse(bag.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDate endDate = LocalDate.parse(bag.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    return startDate.until(endDate, ChronoUnit.DAYS) + 1;
                })
                .sum();

        return new TripInfo(uniqueLocations.size(), totalTripDuration, uniqueLocations);
    }
}