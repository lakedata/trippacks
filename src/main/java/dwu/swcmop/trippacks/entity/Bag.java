package dwu.swcmop.trippacks.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bag")
public class Bag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bagId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private String status;

    private String bagName;

    @Column(name = "travelLocation")
    private String location;

    private Date startDate;

    private Date endDate;
}
