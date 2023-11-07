package dwu.swcmop.trippacks.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TripInfo {
    private int numberOfLocations;
    private int totalTripDuration;
    private List<String> uniqueLocations;

    public TripInfo(int numberOfLocations, int totalTripDuration, List<String> uniqueLocations) {
        this.numberOfLocations = numberOfLocations;
        this.totalTripDuration = totalTripDuration;
        this.uniqueLocations = uniqueLocations;
    }
}
