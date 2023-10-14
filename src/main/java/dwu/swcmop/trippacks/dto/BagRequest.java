package dwu.swcmop.trippacks.dto;

import lombok.*;

@Data
public class BagRequest {
    private String bagName;
    private String location;
    private String startDate;
    private String endDate;
}
