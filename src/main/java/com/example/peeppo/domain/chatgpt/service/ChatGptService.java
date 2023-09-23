package com.example.peeppo.domain.chatgpt.service;

import com.example.peeppo.domain.chatgpt.dto.ChatBotMessage;
import com.example.peeppo.domain.chatgpt.dto.ChatBotRequest;
import com.example.peeppo.domain.chatgpt.dto.ChatBotResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatGptService {

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String openaiApiKey;


    private HttpEntity<ChatBotRequest> getHttpEntity(ChatBotRequest chatRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + openaiApiKey);

        HttpEntity<ChatBotRequest> httpRequest = new HttpEntity<>(chatRequest, headers);
        return httpRequest;
    }

    public ChatBotResponse sendChatBot(ChatBotMessage chatBotMessage) {
        String query = chatBotMessage.getContent() + "이 메시지가 교환요청, 경매, 반품, 마이페이지 중 어떤 키워드에 속해";
        query += "\n위의 문장에서 해당하는 키워드를 '{키워드}' 형식으로 하나만 알려줘";
        query += "\n 키워드";

        ChatBotRequest request1 = new ChatBotRequest(model, query);

        // Call the API
        RestTemplate restTemplate1 = new RestTemplate();
        ChatBotResponse response1 = restTemplate1.postForObject(apiUrl, getHttpEntity(request1), ChatBotResponse.class);

        if (response1 == null || response1.getChoices() == null || response1.getChoices().isEmpty()) {
            throw new RuntimeException();
        }

        return response1;

    }
}
