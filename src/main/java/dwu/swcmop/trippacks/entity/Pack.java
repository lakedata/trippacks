package dwu.swcmop.trippacks.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    @JsonManagedReference // 순환참조 방지
    private Bag bag;

    @Column(name = "pack_name")
    private String packName;

    @Column(name = "is_required")
    private Boolean isRequired;

    @Column(name = "is_personal")
    private Boolean isPersonal;

    @Column(name = "is_completed")
    private Boolean completed;

    @Builder
    public Pack(Bag bag, String packName, Boolean isRequired, Boolean completed){
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
        this.completed = completed;
    }

}


