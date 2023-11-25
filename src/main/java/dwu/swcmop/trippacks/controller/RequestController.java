package dwu.swcmop.trippacks.controller;

import dwu.swcmop.trippacks.dto.RequestRequest;
import dwu.swcmop.trippacks.dto.RequestWithUserInfo;
import dwu.swcmop.trippacks.entity.Bag;
import dwu.swcmop.trippacks.entity.Request;
import dwu.swcmop.trippacks.entity.User;
import dwu.swcmop.trippacks.repository.BagRepository;
import dwu.swcmop.trippacks.repository.RequestRepository;
import dwu.swcmop.trippacks.repository.UserRepository;
import dwu.swcmop.trippacks.service.RequestService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;
    private final BagRepository bagRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;

    @Autowired
    public RequestController(RequestService requestService, BagRepository bagRepository, UserRepository userRepository, RequestRepository requestRepository) {
        this.requestService = requestService;
        this.bagRepository = bagRepository;
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
    }

    @Operation(summary = "짐 요청", description = "친구에게 짐을 요청합니다.")
    @PostMapping("/addReqeset")
    public Request addRequest(RequestRequest request) {
        Bag bag = bagRepository.findByBagId(request.getBagId());

        Request newRequest = Request.builder()
                .bag(bag)
                .fromUserId(request.getFromUserId())
                .requestedProduct(request.getRequestedProduct())
                .toFriendId(request.getToFriendId())
                .isOk(request.getIsOk())
                .build();
        return requestRepository.save(newRequest);
    }

    //짐 출력
    @Operation(summary = "요청짐 출력", description = "가방 ID에 해당하는 짐 리스트 출력합니다.")
    @GetMapping("/getRequestsByBagId")
    public List<RequestWithUserInfo> getRequestsByBagId(@RequestParam Long bagId) {
        List<Request> requests = requestRepository.findByBag_BagId(bagId); // 가방 ID에 해당하는 요청을 검색

        List<RequestWithUserInfo> requestsWithUserInfo = new ArrayList<>();

        for (Request request : requests) {
            Long fromUserId = request.getFromUserId();
            Long toFriendId = request.getToFriendId();

            // fromUserId와 toFriendId를 사용하여 사용자 정보 가져오기
            User fromUser = userRepository.findById(fromUserId).orElse(null);
            User toFriend = userRepository.findById(toFriendId).orElse(null);

            // RequestWithUserInfo에 정보를 추가
            RequestWithUserInfo requestWithUserInfo = new RequestWithUserInfo(request, fromUser, toFriend);
            requestsWithUserInfo.add(requestWithUserInfo);
        }

        return requestsWithUserInfo;
    }

    @Operation(summary = "요청짐 출력", description = "가방주인에게 해당하는 짐 리스트 출력합니다.")
    @GetMapping("/getRequestsByToFriendId")
    public List<RequestWithUserInfo> getRequestsByToFriendId(@RequestParam Long toFriendId) {
        return requestService.getRequestsByToFriendId(toFriendId);
    }

    @Operation(summary = "짐 요청 수락", description = "짐Id를 입력받아 짐 요청을 수락합니다.")
    @PostMapping("/acceptRequest")
    public Request acceptRequest(@RequestParam Long requestId) {
        return requestService.acceptRequest(requestId);
    }

    @Operation(summary = "짐 요청 삭제", description = "짐 요청을 삭제합니다.")
    @DeleteMapping("/deleteRequest")
    public void deleteRequest(@RequestParam Long requestId) {
        requestService.deleteRequest(requestId);
    }
}
