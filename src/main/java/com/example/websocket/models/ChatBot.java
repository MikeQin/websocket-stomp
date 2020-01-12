package com.example.websocket.models;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class ChatBot {

  private static final DateTimeFormatter PATTERN = DateTimeFormatter.ofPattern("HH:mm:ss");

  public String getAnswer(@NotEmpty final String question) {

    String answer = null;

    if (question.toLowerCase().contains("your name") ||
        question.toLowerCase().contains("hello") ||
        question.toLowerCase().contains("hi")) {
      answer = "Hello, my name is Alice. How can I help you?";
    } else if (question.toLowerCase().contains("date")) {
      answer = "Today is " + LocalDate.now();
    } else if (question.toLowerCase().contains("time")) {
      answer = "The current time is " + LocalTime.now().format(PATTERN);
    } else if (question.toLowerCase().contains("what else")) {
      answer = "I know a lot of things. Pleae be specific.";
    } else {
      answer = "Sorry, I didn't understand your question...";
    }

    return answer;
  }
}
