package dwu.swcmop.trippacks.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "friend")
public class Friend {
    @Id
    @Column(name = "fid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "friend_id")
    private Long friendId;

    @Column(name = "kakao_email")
    private String kakaoEmail;

    @Column(name = "is_friend")
    private Boolean isFriend;

    public Friend() {
    }

    public Friend(Long userId, Long friendId, Boolean isFriend) {
        this.userId = userId;
        this.friendId = friendId;
        this.isFriend = isFriend;
    }
}
