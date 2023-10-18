package dwu.swcmop.trippacks.controller;

import dwu.swcmop.trippacks.entity.Request;
import dwu.swcmop.trippacks.service.RequestService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //(1)
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @Operation(summary = "짐 요청", description = "친구에게 짐을 요청합니다.")
    @PostMapping("/create")
    public Request createRequest(@RequestParam Long fromUserId, @RequestParam Long toFriendId, @RequestParam String requestedProduct) {
        Request request = new Request();
        request.setFromUserId(fromUserId);
        request.setToFriendId(toFriendId);
        request.setRequestedProduct(requestedProduct);
        request.setIsOk(false);

        Request createdRequest = requestService.createRequest(request);

        return createdRequest;
    }

    @Operation(summary = "요청짐 출력", description = "가방주인에게 해당하는 짐 리스트 출력합니다.")
    @GetMapping("/getRequestsByToFriendId")
    public List<Request> getRequestsByToFriendId(@RequestParam Long toFriendId) {
        return requestService.getRequestsByToFriendId(toFriendId);
    }

    @Operation(summary = "짐 요청 수락", description = "짐Id를 입력받아 짐 요청을 수락합니다.")
    @PostMapping("/acceptRequest")
    public Request acceptRequest(@RequestParam Long requestId) {
        return requestService.acceptRequest(requestId);
    }

    @Operation(summary = "짐 요청 삭제", description = "짐 요청을 삭제합니다.")
    @PostMapping("/deleteRequest")
    public void deleteRequest(@RequestParam Long requestId) {
        requestService.deleteRequest(requestId);
    }
}
