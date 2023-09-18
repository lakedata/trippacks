package dwu.swcmop.trippacks.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pack")
public class Pack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long packId;

    @ManyToOne
    private Bag bag;

    private String packName;

    private Boolean isRequired;

    private Boolean isPersonal;
}
