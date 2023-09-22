package dwu.swcmop.trippacks.controller;

import dwu.swcmop.trippacks.dto.ChatGptResponse;
import dwu.swcmop.trippacks.dto.QuestionRequest;
import dwu.swcmop.trippacks.service.ChatGptService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat-gpt")
public class ChatGptController {

    private final ChatGptService chatGptService;

    public ChatGptController(ChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    @PostMapping("/question")
    public ChatGptResponse sendQuestion(@RequestBody QuestionRequest requestDto) {
        return chatGptService.askQuestion(requestDto);
    }
}