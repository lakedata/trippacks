package dwu.swcmop.trippacks.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "request")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "requested_product") // 물품 컬럼의 이름을 명확하게 지정
    private String requestedProduct;

    @Column(name = "from_user_id")
    private Long fromUserId; // User를 Long으로 변경

    @Column(name = "to_friend_id")
    private Long toFriendId; // Friend를 Long으로 변경

    @Column(name = "is_ok")
    private Boolean isOk;
}

