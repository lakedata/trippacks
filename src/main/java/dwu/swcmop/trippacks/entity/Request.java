package dwu.swcmop.trippacks.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.type.TypeFactory;

import javax.persistence.*;


@Entity
@Getter @Setter
@NoArgsConstructor
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


    @ManyToOne
    @JoinColumn(name = "bag_id")
    @JsonManagedReference // 순환참조 방지
    private Bag bag;

    @Builder
    public Request(Long requestId, String requestedProduct, Long fromUserId, Long toFriendId, Boolean isOk, Bag bag) {
        this.requestId = requestId;
        this.requestedProduct = requestedProduct;
        this.fromUserId = fromUserId;
        this.toFriendId = toFriendId;
        this.isOk = isOk;
        this.bag = bag;
    }
}

