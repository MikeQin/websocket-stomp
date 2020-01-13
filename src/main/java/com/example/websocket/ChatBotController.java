package com.example.websocket;

import com.example.websocket.models.Answer;
import com.example.websocket.models.ChatBot;
import com.example.websocket.models.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class ChatBotController {

  private static final DateTimeFormatter PATTERN = DateTimeFormatter.ofPattern("HH:mm:ss");

  private ChatBot chatBot;
  private SimpMessagingTemplate template;

  public ChatBotController(ChatBot chatBot, SimpMessagingTemplate template) {

    this.chatBot = chatBot;
    this.template = template;
  }

  /**
   * This is the message handler method. Asynchronous processing
   * <p>
   * Destination: /ask
   * Payload of the message: Question
   * <p>
   * Mapping a Message onto a message-handling method.
   * <p>
   * The return value is broadcast to all subsribers to "/topic/answers"
   *
   * @param question The question from client
   * @return CompletableFuture<Answer>
   */
  @MessageMapping("/ask")
  @SendTo("/topic/answers")
  public CompletableFuture<Answer> getAnswer(@Payload Question question) {

    String questionStr = HtmlUtils.htmlEscape(question.getContent());
    log.info("[**REQ] Question: {}", questionStr);

    return CompletableFuture.supplyAsync(() -> {

      try {
        TimeUnit.SECONDS.sleep(1); // simulate server's delay in processing
      } catch (InterruptedException e) {
        throw new IllegalStateException(e);
      }

      Answer answer = new Answer(chatBot.getAnswer(questionStr));

      // Send to /topic/audit
      template.convertAndSend("/topic/audit", LocalTime.now().format(PATTERN) + ": " + questionStr);
      template.convertAndSend("/topic/audit", LocalTime.now().format(PATTERN) + ": " + answer.getContent());

      return answer;
    });
  }

  @SubscribeMapping("/server-time")
  public String getServerTime() {

    String time = "[**] server.time: " + LocalTime.now().format(PATTERN);
    log.info("[time] {}", time);

    return time;

  }
}
