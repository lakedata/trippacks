package dwu.swcmop.trippacks.dto;

import lombok.Data;

@Data
public class PackRequest {
    private Long bagId;
    private String packName;
    private Boolean isRequired;
}
