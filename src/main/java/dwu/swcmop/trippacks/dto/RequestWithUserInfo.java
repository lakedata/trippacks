package dwu.swcmop.trippacks.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dwu.swcmop.trippacks.entity.Bag;
import dwu.swcmop.trippacks.entity.Request;
import dwu.swcmop.trippacks.entity.User;

import java.util.List;

public class RequestWithUserInfo {
    private Request request;
    private Long fromUserId;
    private String fromUserKakaoProfileImg;
    private String fromUserKakaoNickname;
    private String fromUserKakaoEmail;

    private Long toFriendId;
    private String toFriendKakaoProfileImg;
    private String toFriendKakaoNickname;
    private String toFriendKakaoEmail;

    // Getter 메서드

    public Request getRequest() {
        return request;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public String getFromUserKakaoProfileImg() {
        return fromUserKakaoProfileImg;
    }

    public String getFromUserKakaoNickname() {
        return fromUserKakaoNickname;
    }

    public String getFromUserKakaoEmail() {
        return fromUserKakaoEmail;
    }

    public Long getToFriendId() {
        return toFriendId;
    }

    public String getToFriendKakaoProfileImg() {
        return toFriendKakaoProfileImg;
    }

    public String getToFriendKakaoNickname() {
        return toFriendKakaoNickname;
    }

    public String getToFriendKakaoEmail() {
        return toFriendKakaoEmail;
    }

    public RequestWithUserInfo(Request request, User fromUser, User toFriend) {
        this.request = request;
        this.fromUserId = fromUser.getUserCode();
        this.fromUserKakaoProfileImg = fromUser.getKakaoProfileImg();
        this.fromUserKakaoNickname = fromUser.getKakaoNickname();
        this.fromUserKakaoEmail = fromUser.getKakaoEmail();

        this.toFriendId = toFriend.getUserCode();
        this.toFriendKakaoProfileImg = toFriend.getKakaoProfileImg();
        this.toFriendKakaoNickname = toFriend.getKakaoNickname();
        this.toFriendKakaoEmail = toFriend.getKakaoEmail();
    }
}

