package dwu.swcmop.trippacks.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BagRequest {
    private String status;
    private String bagName;
    private String location;
    private String startDate;
    private String endDate;
}
