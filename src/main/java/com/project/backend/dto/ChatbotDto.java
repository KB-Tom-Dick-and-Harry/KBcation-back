package com.project.backend.dto;

import com.project.backend.model.Chatbot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatbotDto {

    private Long memberId;
    private Long chatBotId;
    private String question;
    private String answer;

    // Chatbot 엔티티를 ChatbotResponseDto로 변환하는 메서드
    public static ChatbotDto fromEntity(Chatbot chatbot) {
        return ChatbotDto.builder()
                .chatBotId(chatbot.getChatBotId())
                .memberId(chatbot.getMemberId())
                .question(chatbot.getQuestion())
                .answer(chatbot.getAnswer())
                .build();
    }
}
