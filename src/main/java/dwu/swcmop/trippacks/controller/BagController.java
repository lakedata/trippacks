package dwu.swcmop.trippacks.controller;

import dwu.swcmop.trippacks.dto.BagRequest;
import dwu.swcmop.trippacks.dto.PackRequest;
import dwu.swcmop.trippacks.entity.Bag;
import dwu.swcmop.trippacks.entity.Pack;
import dwu.swcmop.trippacks.service.BagService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


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
}