package com.project.backend.service;

import com.project.backend.dto.ChatbotDto;
import java.util.List;

public interface ChatbotService {
    // LLM 서버와 통신하여 챗봇 응답을 생성하고 저장
    Long createChatbot(ChatbotDto requestDto);

    // 특정 사용자의 모든 챗봇 대화 내역 조회
    List<ChatbotDto> getAllChatbotsByMemberId(Long memberId);

    // 모든 챗봇 질문 조회
    List<ChatbotDto> getAllChatbots();

    // 특정 챗봇 질문 조회
    ChatbotDto getChatbot(Long chatBotId);

    // 챗봇 답변 수동 업데이트 (필요한 경우)
    void updateChatbotAnswer(Long chatBotId, String answer);

    // 챗봇 질문 삭제
    void deleteChatbot(Long chatBotId);
}