package dwu.swcmop.trippacks.controller;

import dwu.swcmop.trippacks.dto.PackRequest;
import dwu.swcmop.trippacks.entity.Bag;
import dwu.swcmop.trippacks.entity.Pack;
import dwu.swcmop.trippacks.entity.User;
import dwu.swcmop.trippacks.repository.BagRepository;
import dwu.swcmop.trippacks.service.PackService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class PackController {
    private PackService packService;
    private final BagRepository bagRepository;



    @GetMapping("/pack/list/{bagId}")
    public Map<String, Object> getPackList(@PathVariable Long bagId) {
        Bag bag = bagRepository.findByBagId(bagId);
        User user = bag.getUser(); // Retrieve the associated User entity

        Map<String, Object> response = new HashMap<>();
        response.put("bag", bag);

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("kakaoId", user.getKakaoId());
        userMap.put("kakaoProfileImg", user.getKakaoProfileImg());
        userMap.put("kakaoNickname", user.getKakaoNickname());
        userMap.put("kakaoEmail", user.getKakaoEmail());
        userMap.put("userCode", user.getUserCode());

        response.put("user", userMap);

        List<Pack> packs = packService.findAllPack(bagId);
        response.put("packs", packs);

        return response;
}
//    @GetMapping("/pack/list/{bagId}")
//    public List<Pack> getPackList(@PathVariable Long bagId){
//
//        return packService.findAllPack(bagId);
//    }

    @GetMapping("/pack/{id}")
    public Map<String, Object> getPack(@PathVariable("id") Long id){
        Pack pack = packService.findPack(id);
        Bag bag = pack.getBag(); // Retrieve the associated Bag entity
        User user = bag.getUser(); // Retrieve the associated User entity

        Map<String, Object> response = new HashMap<>();
        response.put("pack", pack);

        // Include user information
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("kakaoId", user.getKakaoId());
        userMap.put("kakaoProfileImg", user.getKakaoProfileImg());
        userMap.put("kakaoNickname", user.getKakaoNickname());
        userMap.put("kakaoEmail", user.getKakaoEmail());
        userMap.put("userCode", user.getUserCode());

        response.put("user", userMap);

        return response;
    }

//    @GetMapping("/pack/{id}")
//    public Pack getPack(@PathVariable("id") Long id){
//        return packService.findPack(id);
//    }
    @PostMapping("/pack")
    public Pack addPack(@RequestBody PackRequest pack ){
        return packService.addPack(pack);
    }

    @PutMapping("/pack/{id}")
    public Pack updatePack(@RequestBody PackRequest pack, @PathVariable("id") Long id){
        return packService.update(id, pack);
    }

    @DeleteMapping("/pack/{id}")
    public void removePack(@PathVariable("id") Long id){
        packService.removePack(id);
    }

}
