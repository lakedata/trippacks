package dwu.swcmop.trippacks.entity;

import javax.persistence.*;

@Entity
@Table(name = "request")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_friend_id")
    private Friend toFriend;

    @Column(name = "is_ok")
    private Boolean isOk;

}

