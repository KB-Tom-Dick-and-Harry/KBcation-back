package com.project.backend.service;

import com.project.backend.dto.ChatbotDto;
import com.project.backend.dto.ChatbotDto;
import com.project.backend.model.Chatbot;
import com.project.backend.repository.ChatbotRepository;
import com.project.backend.service.ChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatbotServiceImpl implements ChatbotService {

    private final ChatbotRepository chatbotRepository;

    // 챗봇 질문 생성
    @Override
    @Transactional
    public Long createChatbot(ChatbotDto requestDto) {
        Chatbot chatbot = Chatbot.builder()
                .memberId(requestDto.getMemberId())
                .question(requestDto.getQuestion())
                .answer(requestDto.getAnswer())
                .build();
        return chatbotRepository.save(chatbot).getChatBotId();
    }

    // 모든 챗봇 질문 조회
    @Override
    @Transactional(readOnly = true)
    public List<ChatbotDto> getAllChatbots() {
        return chatbotRepository.findAll().stream()
                .map(ChatbotDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 특정 챗봇 질문 조회
    @Override
    @Transactional(readOnly = true)
    public ChatbotDto getChatbot(Long chatBotId) {
        Chatbot chatbot = chatbotRepository.findById(chatBotId)
                .orElseThrow(() -> new IllegalArgumentException("해당 챗봇 질문이 없습니다. ID: " + chatBotId));
        return ChatbotDto.fromEntity(chatbot);
    }

    // 챗봇 답변 업데이트
    @Override
    @Transactional
    public void updateChatbotAnswer(Long chatBotId, String answer) {
        Chatbot chatbot = chatbotRepository.findById(chatBotId)
                .orElseThrow(() -> new IllegalArgumentException("해당 챗봇 질문이 없습니다. ID: " + chatBotId));
        chatbot.updateAnswer(answer);
        chatbotRepository.save(chatbot);
    }

    // 챗봇 질문 삭제
    @Override
    @Transactional
    public void deleteChatbot(Long chatBotId) {
        if (!chatbotRepository.existsById(chatBotId)) {
            throw new IllegalArgumentException("해당 챗봇 질문이 없습니다. ID: " + chatBotId);
        }
        chatbotRepository.deleteById(chatBotId);
    }
}
