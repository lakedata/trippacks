package dwu.swcmop.trippacks.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class InvitationRequest {
    @NotNull
    int bagId;
}
