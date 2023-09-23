package com.example.peeppo.domain.chatgpt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChatBotResponse {

        private List<Choice> choices;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Choice {

            private int index;
            private ChatBotMessage message;

        }


}
