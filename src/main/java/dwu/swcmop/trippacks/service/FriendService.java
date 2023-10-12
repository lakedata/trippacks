package dwu.swcmop.trippacks.service;

import dwu.swcmop.trippacks.config.BaseException;
import dwu.swcmop.trippacks.dto.FriendRequest;
import dwu.swcmop.trippacks.dto.FriendResponse;
import dwu.swcmop.trippacks.entity.Friend;
import dwu.swcmop.trippacks.entity.User;
import dwu.swcmop.trippacks.repository.FriendRepository;
import dwu.swcmop.trippacks.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;


import static dwu.swcmop.trippacks.config.BaseResponseStatus.*;

@Service
public class FriendService {
    @Autowired
    private UserService userService;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    public FriendResponse createFriend(FriendRequest friend) throws BaseException {
        Friend saveFriend = null;
        User user = null;

        try {
            Friend newFriend = new Friend(friend.getUserId(), friend.getFriendId(), false);
            Friend existingFriend = friendRepository.findUserEntityByUserIdAndFriendId(friend.getUserId(), friend.getFriendId());


            if (existingFriend == null) {// 이미 저장된 데이터가 없으면 저장
                user = userRepository.findByUserCode(friend.getFriendId());
                saveFriend = friendRepository.save(newFriend);
            } else {
                throw new BaseException(EXIST_FRIEND_FAIL);
            }
        } catch (Exception e) {
            throw new BaseException(ADD_FRIEND_FAIL);
        }
        String userEmail = (user != null) ? user.getKakaoEmail() : null;
        FriendResponse bagResponse = new FriendResponse(saveFriend.getId(), saveFriend.getUserId(), saveFriend.getFriendId(), userEmail, saveFriend.getIsFriend());

        return bagResponse;
    }


    public int acceptFriend(long Id, long fId) throws BaseException {
        Friend friend = friendRepository.findUserEntityByUserIdAndFriendId(Id, fId);
        if (friend == null) {
            throw new BaseException(FRIEND_IS_EMPTY);
        }

        friend.setIsFriend(true);//수락

        friendRepository.save(friend);

        return 1;
    }


    @Transactional
    public int deleteFriend(long Id, long fId) throws BaseException {
        User user = userService.findById(Id);
        if (user == null) {
            throw new BaseException(INVALID_JWT);
        }
        Friend getFriend = friendRepository.findUserEntityByUserIdAndFriendId(Id, fId);
        if (getFriend == null) {
            throw new BaseException(FRIEND_IS_EMPTY);
        }
        try {
            friendRepository.delete(getFriend);
        } catch (Exception e) {
            throw new BaseException(DELETE_FRIEND_FAIL);
        }
        return 1;
    }

}
