package dwu.swcmop.trippacks.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.security.Timestamp;

@Entity//JPA가 데이터 바인딩
@Data
@NoArgsConstructor//기본 생성자 만들어줌, 갑이 비어도 됨
@Table(name = "user_master") //(1)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //(2)
    @Column(name = "user_code") //(3)
    private Long userCode;

    @Column(name = "kakao_id")
    private Long kakaoId;

    @Column(name = "kakao_profile_img")
    private String kakaoProfileImg;

    @Column(name = "kakao_nickname")
    private String kakaoNickname;

    @Column(name = "kakao_email")
    private String kakaoEmail;

    @Column(name = "user_role")
    private String userRole;

    @Column(name = "create_time")
    @CreatedDate//DB에서 current_timestamp설정시 사용
    private Timestamp createTime;//유저 관리용 시간

    @Builder
    public User(Long kakaoId, String kakaoProfileImg, String kakaoNickname,
                String kakaoEmail, String userRole) {

        this.kakaoId = kakaoId;
        this.kakaoProfileImg = kakaoProfileImg;
        this.kakaoNickname = kakaoNickname;
        this.kakaoEmail = kakaoEmail;
        this.userRole = userRole;
    }
}
//user db바꿔서 연관되어있는 db나중에 수정 예정..(아직 안함)

//    @Entity
//    @Table(name = "users")
//    public class User {
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        @Column(name = "user_id")
//        private Long userId;
//
//        @Column(name = "kakao_email")
//        private String kakaoEmail;
//
//        @Column(name = "kakao_name")
//        private String kakaoName;
//
//        @Column(name = "kakao_password")
//        private String kakaoPassword;
//
//        @Column(name = "profile_url")
//        private String profileUrl;
//
//    }
