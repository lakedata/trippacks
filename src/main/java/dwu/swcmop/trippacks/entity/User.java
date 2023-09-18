package dwu.swcmop.trippacks.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String kakaoEmail;

    private String kakaoName;

    private String kakaoPassword;

    private String profile;
}
