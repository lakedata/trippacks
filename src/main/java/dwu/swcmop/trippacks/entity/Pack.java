package dwu.swcmop.trippacks.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "pack")
@NoArgsConstructor
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

    @Builder
    public Pack(Bag bag, String packName, Boolean isRequired){
        this.bag = bag;
        this.packName = packName;
        if(isRequired == true){
            this.isRequired = true;
            this.isPersonal = false;
        }
        else{
            this.isPersonal = true;
            this.isRequired = false;
        }
    }

}


