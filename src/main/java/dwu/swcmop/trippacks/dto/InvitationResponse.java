package dwu.swcmop.trippacks.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InvitationResponse {
    private int id;
    private int bagId;
    private String slug;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
