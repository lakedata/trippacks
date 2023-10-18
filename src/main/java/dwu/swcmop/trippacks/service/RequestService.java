package dwu.swcmop.trippacks.service;

import dwu.swcmop.trippacks.entity.Friend;
import dwu.swcmop.trippacks.entity.Request;
import dwu.swcmop.trippacks.repository.FriendRepository;
import dwu.swcmop.trippacks.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService {
    private final RequestRepository requestRepository;
    private final FriendRepository friendRepository;


    @Autowired
    public RequestService(RequestRepository requestRepository, FriendRepository friendRepository) {
        this.requestRepository = requestRepository;
        this.friendRepository = friendRepository;
    }

    public Request createRequest(Request request) {
        List<Friend> friends = friendRepository.findFriendsWithUserIds(request.getFromUserId(), request.getToFriendId());

        if (!friends.isEmpty()) {
            return requestRepository.save(request);
        } else {
            return null; // 친구 아니면
        }
    }

    public List<Request> getRequestsByToFriendId(Long toFriendId) {
        return requestRepository.findByToFriendId(toFriendId);
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
