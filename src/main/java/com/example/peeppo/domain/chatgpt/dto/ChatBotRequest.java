package com.example.peeppo.domain.chatgpt.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatBotRequest {

    private String model;
    private List<ChatBotMessage> messages;
    private int n;
    private double temperature;

    public ChatBotRequest(String model, String prompt) {
        this.model = model;

        this.messages = new ArrayList<>();
        this.messages.add(new ChatBotMessage("user", prompt));

        this.n = 1;
        this.temperature = 0.5;
    }
}
