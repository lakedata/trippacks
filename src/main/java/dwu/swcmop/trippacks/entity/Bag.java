package dwu.swcmop.trippacks.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bag")
public class Bag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bag_id")
    private Long bagId;

    @ManyToOne
    @JoinColumn(name = "user_code")
    private User user;

    @Column(name = "status")
    private String status;

    @Column(name = "bag_name")
    private String bagName;

    @Column(name = "travel_location")
    private String location;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;


    @OneToMany(mappedBy = "bag")
    private List<Pack> pack = new ArrayList<>();
}
