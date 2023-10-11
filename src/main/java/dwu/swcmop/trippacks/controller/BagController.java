package dwu.swcmop.trippacks.controller;

import dwu.swcmop.trippacks.config.BaseException;
import dwu.swcmop.trippacks.config.BaseResponse;
import dwu.swcmop.trippacks.dto.BagRequest;
import dwu.swcmop.trippacks.dto.BagResponse;
import dwu.swcmop.trippacks.service.BagService;
import io.swagger.annotations.ApiOperation;
import org.hibernate.mapping.Bag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/bag")
public class BagController {
    private final BagService bagService;

    @Autowired
    public BagController(BagService bagService) {
        this.bagService = bagService;
    }

    @ApiOperation(value = "가방 생성", notes = "짐을 추가한다.")
    @PostMapping("/add")
    public BaseResponse<BagResponse> create(@RequestBody @Valid BagRequest bagRequest) {
        try {

            BagResponse bagResponse = bagService.createBag(bagRequest);

            return new BaseResponse<>(bagResponse);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

}