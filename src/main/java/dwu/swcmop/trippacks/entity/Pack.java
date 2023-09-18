package dwu.swcmop.trippacks.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pack")
public class Pack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pack_id")
    private Long packId;

    @ManyToOne
    @JoinColumn(name = "bag_id")
    private Bag bag;

    @Column(name = "pack_name")
    private String packName;

    @Column(name = "is_required")
    private Boolean isRequired;

    @Column(name = "is_personal")
    private Boolean isPersonal;
}
