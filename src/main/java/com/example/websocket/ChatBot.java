package com.example.websocket;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class ChatBot {

    public String getAnswer(@NotEmpty final String question) {

        String answer = null;

        if(question.toLowerCase().contains("your name")) {
            answer = "Hello, my name is Alice. How can I help you?";
        }
        else if (question.toLowerCase().contains("date")) {
            answer = "Today is " + LocalDate.now();
        }
        else if (question.toLowerCase().contains("time")) {
            answer = "The current time is " + LocalTime.now();
        }
        else if (question.toLowerCase().contains("what else")) {
            answer = "I know a lot of things. Pleae be specific.";
        }
        else {
            answer = "Sorry, I didn't understand your question...";
        }

        return answer;
    }
}
