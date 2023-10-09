package dwu.swcmop.trippacks.dto;

import lombok.*;

//@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BagResponse {
    private Long bagId;
    //private Long userId;
    private String status;
    private String bagName;
    private String location;
    private String startDate;
    private String endDate;
}
