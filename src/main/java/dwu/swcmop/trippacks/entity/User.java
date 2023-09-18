package dwu.swcmop.trippacks.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "kakao_email")
    private String kakaoEmail;

    @Column(name = "kakao_name")
    private String kakaoName;

    @Column(name = "kakao_password")
    private String kakaoPassword;

    @Column(name = "profile_url")
    private String profileUrl;
}
