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

    @GetMapping("/bag/list")
    public List<Bag> getBagList(@RequestParam Long kakaoId){
        return bagService.findAllBag(kakaoId);
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
}