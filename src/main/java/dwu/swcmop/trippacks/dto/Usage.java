package dwu.swcmop.trippacks.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usage {
    @JsonProperty("prompt_tokens")
    private int promptTokens;
    @JsonProperty("completion_tokens")
    private int completionTokens;
    @JsonProperty("total_tokens")
    private int totalTokens;
}