package dwu.swcmop.trippacks.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "bag")
public class Bag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bag_id")
    private Long bagId;

    @ManyToOne
    @JoinColumn(name = "user_id")
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

    public Bag(String status, String bagName, String location, String startDate, String endDate) {
        this.status = status;
        this.bagName = bagName;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
