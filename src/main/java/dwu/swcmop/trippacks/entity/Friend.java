package dwu.swcmop.trippacks.entity;

import javax.persistence.*;

@Entity
@Table(name = "friend")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendId;

    @ManyToOne
    private User user;

    private String kakaoEmail;

    private Boolean isFriend;
}
