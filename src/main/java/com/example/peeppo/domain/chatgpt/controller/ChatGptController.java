package com.example.peeppo.domain.chatgpt.controller;

import com.example.peeppo.domain.chatgpt.dto.ChatBotMessage;
import com.example.peeppo.domain.chatgpt.dto.ChatBotResponse;
import com.example.peeppo.domain.chatgpt.service.ChatGptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/chat-bot")
@RestController
@RequiredArgsConstructor
public class ChatGptController {

    private final ChatGptService chatGptService;

    @PostMapping
    public ChatBotResponse sendChatBot(@RequestBody ChatBotMessage chatBotMessage){
        return chatGptService.sendChatBot(chatBotMessage);
    }

}
