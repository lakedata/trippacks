package dwu.swcmop.trippacks.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dwu.swcmop.trippacks.dto.BagStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "bag")
public class Bag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bag_id")
    private Long bagId;

    @JsonBackReference    // @JsonIgnoreProperties("bags")
    @ManyToOne
    @JoinColumn(name = "kakao_id")
    private User user;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private BagStatus status;

    @Column(name = "bag_name")
    private String bagName;

    @Column(name = "travel_location")
    private String location;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "kakao") //kakao_id -> kakao 변경
    private Long kakaoId;

    @JsonBackReference //순환참조 방지
    @OneToMany(mappedBy = "bag",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pack> pack = new ArrayList<>();

    @JsonBackReference //순환참조 방지
    @OneToMany(mappedBy = "bag",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Request> request = new ArrayList<>();


    @JsonBackReference //순환참조 방지
    @OneToMany(mappedBy = "bag", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Invitation> invitations = new ArrayList<>();

    @Builder
    public Bag(User user, String location, String bagName, String startDate, String endDate, Long kakaoId) {
        this.user = user;
        this.location = location;
        this.bagName = bagName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.kakaoId = kakaoId;
    }
}
