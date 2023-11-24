package dwu.swcmop.trippacks.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dwu.swcmop.trippacks.entity.Bag;
import lombok.*;

//@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BagResponse {
    private Long bagId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long userCode;
    private Long kakaoId;
    private String status;
    private String bagName;
    private String location;
    private String startDate;
    private String endDate;

    public static BagResponse fromEntity(Bag bag) {
        BagResponse bagResponse = new BagResponse();
        bagResponse.setBagId(bag.getBagId());
        bagResponse.setUserCode(bag.getUser().getUserCode());
        bagResponse.setKakaoId(bag.getUser().getKakaoId());
        bagResponse.setStatus(bag.getStatus().name());
        bagResponse.setBagName(bag.getBagName());
        bagResponse.setLocation(bag.getLocation());
        bagResponse.setStartDate(bag.getStartDate());
        bagResponse.setEndDate(bag.getEndDate());
        bagResponse.setKakaoId(bag.getKakaoId());

        return bagResponse;
    }
}
