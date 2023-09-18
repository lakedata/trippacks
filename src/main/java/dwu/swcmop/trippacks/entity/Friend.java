package dwu.swcmop.trippacks.entity;

import javax.persistence.*;

@Entity
@Table(name = "friend")
public class Friend {
    @Id
    @Column(name = "friend_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "kakao_email")
    private String kakaoEmail;

    @Column(name = "is_friend")
    private Boolean isFriend;
}
