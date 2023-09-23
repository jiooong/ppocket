package com.example.peeppo.domain.chatgpt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatBotMessage {
    private String role;
    private String content;
}
