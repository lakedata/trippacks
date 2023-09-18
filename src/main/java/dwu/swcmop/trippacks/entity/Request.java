package dwu.swcmop.trippacks.entity;

import javax.persistence.*;

@Entity
@Table(name = "request")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "fromUser")
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "toUser")
    private Friend toFriend;

    private Boolean isOk;

}

