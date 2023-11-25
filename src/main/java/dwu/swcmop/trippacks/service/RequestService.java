package dwu.swcmop.trippacks.service;

import dwu.swcmop.trippacks.dto.RequestWithUserInfo;
import dwu.swcmop.trippacks.entity.Friend;
import dwu.swcmop.trippacks.entity.Request;
import dwu.swcmop.trippacks.entity.User;
import dwu.swcmop.trippacks.repository.FriendRepository;
import dwu.swcmop.trippacks.repository.RequestRepository;
import dwu.swcmop.trippacks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {
    private final RequestRepository requestRepository;
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;


    @Autowired
    public RequestService(RequestRepository requestRepository, FriendRepository friendRepository, UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }

    public Request createRequest(Request request) {
        List<Friend> friends = friendRepository.findFriendsWithUserIds(request.getFromUserId(), request.getToFriendId());

        if (!friends.isEmpty()) {
            return requestRepository.save(request);
        } else {
            return null; // 친구 아니면
        }
    }

    public List<RequestWithUserInfo> getRequestsByToFriendId(Long toFriendId) {
        List<Request> requests = requestRepository.findByToFriendId(toFriendId);
        List<RequestWithUserInfo> requestsWithUserInfo = new ArrayList<>();

        for (Request request : requests) {
            Long fromUserId = request.getFromUserId();
            toFriendId = request.getToFriendId();

            // fromUserId와 toFriendId를 사용하여 사용자 정보 가져옴
            User fromUser = userRepository.findById(fromUserId).orElse(null);
            User toFriend = userRepository.findById(toFriendId).orElse(null);

            // RequestWithUserInfo에 정보를 추가
            RequestWithUserInfo requestWithUserInfo = new RequestWithUserInfo(request, fromUser, toFriend);
            requestsWithUserInfo.add(requestWithUserInfo);
        }

        return requestsWithUserInfo;
    }

    public Request acceptRequest(Long requestId) {
        Optional<Request> optionalRequest = requestRepository.findById(requestId);

        if (optionalRequest.isPresent()) {
            Request request = optionalRequest.get();
            request.setIsOk(true); // 수락하면 isOk를 true로 설정
            return requestRepository.save(request);
        } else {
            return null;// 요청이 존재하지 않음을 처리
        }
    }

    public void deleteRequest(Long requestId) {
        Optional<Request> optionalRequest = requestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            requestRepository.delete(optionalRequest.get());
        } else {
            // 요청이 존재하지 않음을 처리
        }
    }
}
