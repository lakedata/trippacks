package dwu.swcmop.trippacks.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
public class QuestionRequest implements Serializable {
    private String question;
}