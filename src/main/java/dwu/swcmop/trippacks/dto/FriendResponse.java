package dwu.swcmop.trippacks.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendResponse {
    private Long id;
    private Long userId;
    private Long friendId;
    private String kakaoEmail;
    private Boolean isFriend;
}
