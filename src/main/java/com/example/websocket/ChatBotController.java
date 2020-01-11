package com.example.websocket;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@RestController
public class ChatBotController {

    private ChatBot chatBot;

    ChatBotController(ChatBot chatBot) {
        this.chatBot = chatBot;
    }

    /**
     * This is the message handler method. Asynchronous processing
     *
     * Destination: /ask
     * Payload of the message: Question
     *
     * Mapping a Message onto a message-handling method.
     *
     * The return value is broadcast to all subsribers to "/topic/answers"
     *
     * @param question
     * @return
     * @throws Exception
     */
    @MessageMapping("/ask")
    @SendTo("/topic/answers")
    public ResponseEntity<Answer> getAnswer(Question question) throws Exception {

        Thread.sleep(1000); // simulate delay
        String q = HtmlUtils.htmlEscape(question.getContent());
        Answer answer = new Answer(chatBot.getAnswer(q));

        return ResponseEntity.ok(answer);
    }
}
