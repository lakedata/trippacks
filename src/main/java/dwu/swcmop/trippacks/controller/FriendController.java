package dwu.swcmop.trippacks.controller;

import dwu.swcmop.trippacks.config.BaseException;
import dwu.swcmop.trippacks.config.BaseResponse;
import dwu.swcmop.trippacks.dto.FriendRequest;
import dwu.swcmop.trippacks.dto.FriendResponse;
import dwu.swcmop.trippacks.entity.Friend;
import dwu.swcmop.trippacks.repository.FriendRepository;
import dwu.swcmop.trippacks.service.FriendService;
import dwu.swcmop.trippacks.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dwu.swcmop.trippacks.config.BaseResponseStatus.ACCEPT_FRIEND_FAIL;
import static dwu.swcmop.trippacks.config.BaseResponseStatus.DELETE_FRIEND_FAIL;


@AllArgsConstructor
@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private FriendService friendService;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "친구 추가", notes = "친구를 추가한다.")
    @PostMapping("/add")
    public BaseResponse<FriendResponse> create(@RequestBody @Valid FriendRequest friendRequest) {
        try {

            FriendResponse bagResponse = friendService.createFriend(friendRequest);

            return new BaseResponse<>(bagResponse);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //친구 수락
    @PatchMapping("/accept/{userId}/{friendId}")
    @Operation(summary = "친구 수락", description = "사용자Id, 친구Id 입력받아 친구 수락합니다.")
    public BaseResponse<String> acceptFriend(@PathVariable("userId") long Id, @PathVariable("friendId") long fId) {
        try {
            int result = friendService.acceptFriend(Id, fId);
            if (result != 1) {
                throw new BaseException(ACCEPT_FRIEND_FAIL);
            } else {
                return new BaseResponse<>("친구 수락에 성공했습니다.");
            }
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/friendList/{userId}")
    @Operation(summary = "친구 list 조회", description = "내 Id를 입력받아 친구 목록 조회한다.")
    public BaseResponse<List<Long>> getFriendsList(@PathVariable("userId") Long userId) {
        try {
            List<Friend> friends = friendRepository.findByUserIdAndIsFriend(userId, true);
            friends.addAll(friendRepository.findByFriendIdAndIsFriend(userId, true));

            List<Long> friendIds = friends.stream()
                    .filter(friend -> friend.getIsFriend() != null && friend.getIsFriend())
                    .flatMap(friend -> {
                        List<Long> ids = new ArrayList<>();
                        if (friend.getFriendId().equals(userId)) {
                            ids.add(friend.getUserId());  // If friendId equals userId, add userId
                        }
                        if (friend.getUserId().equals(userId)) {
                            ids.add(friend.getFriendId());  // If userId equals friendId, add friendId
                        }
                        return ids.stream();
                    })
                    .collect(Collectors.toList());


            return new BaseResponse<>(friendIds);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/delete/{userId}/{friendId}")
    @Operation(summary = "친구 삭제", description = "사용자Id, 친구Id 입력받아 친구 삭제를 삭제합니다.")
    public BaseResponse<String> deleteFriend(@PathVariable("userId") long Id, @PathVariable("friendId") long fId) {
        try {
            long userId = Long.parseLong(String.valueOf(Id));
            long friendId = Long.parseLong(String.valueOf(fId));

            int result = friendService.deleteFriend(userId, friendId);
            if (result != 1) {
                throw new BaseException(DELETE_FRIEND_FAIL);
            } else {
                return new BaseResponse<>("친구 삭제에 성공했습니다.");
            }
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}