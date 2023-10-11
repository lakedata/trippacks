package dwu.swcmop.trippacks.controller;

import dwu.swcmop.trippacks.entity.Bag;
import dwu.swcmop.trippacks.entity.Pack;
import dwu.swcmop.trippacks.service.PackService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class PackController {
    private PackService packService;

//    @GetMapping("/pack/list/{bag}")
//    public List<Pack> getPackList(@PathVariable("bag") Bag bag){
//        return packService.findAllPack(bag);
//    }

    @GetMapping("/pack/{id}")
    public Optional<Pack> getPack(@PathVariable("id") Long id){
        return packService.findPack(id);
    }

//    @PostMapping("/pack")
//    public Pack addPack(@RequestBody Pack pack){
//        return packService.addPack(pack);
//    }

    @PutMapping("/pack/{id}")
    public Pack updatePack(@RequestBody Pack pack, @PathVariable("id") Long id){
        return packService.update(id, pack);
    }

    @DeleteMapping("/pack/{id}")
    public void removePack(@PathVariable("id") Long id){
        packService.removePack(id);
    }

}
