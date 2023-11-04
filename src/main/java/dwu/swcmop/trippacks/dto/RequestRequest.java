package dwu.swcmop.trippacks.dto;

import lombok.Data;

@Data
public class RequestRequest {
    private Long bagId;
    private String requestedProduct;
    private Long fromUserId;
    private Long toFriendId;
    private Boolean isOk;
}
