package com.project.backend.service;

import com.project.backend.dto.ChatbotDto;

import java.util.List;

public interface ChatbotService {
    // 챗봇 질문 생성
    Long createChatbot(ChatbotDto requestDto);

    // 모든 챗봇 질문 조회
    List<ChatbotDto> getAllChatbots();

    // 특정 챗봇 질문 조회
    ChatbotDto getChatbot(Long chatBotId);

    // 챗봇 답변 업데이트
    void updateChatbotAnswer(Long chatBotId, String answer);

    // 챗봇 질문 삭제
    void deleteChatbot(Long chatBotId);
}
