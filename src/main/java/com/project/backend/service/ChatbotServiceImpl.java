package com.project.backend.service;

import com.project.backend.dto.ChatbotDto;
import com.project.backend.model.Chatbot;
import com.project.backend.repository.ChatbotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatbotServiceImpl implements ChatbotService {

    private final ChatbotRepository chatbotRepository;
    private final WebClient webClient;

    @Value("${llm.server.url}")
    private String llmServerUrl;

    @Override
    @Transactional
    public Long createChatbot(ChatbotDto requestDto) {
        try {
            log.info("FastAPI 서버로 요청 전송: question={}, memberId={}",
                    requestDto.getQuestion(), requestDto.getMemberId());

            // FastAPI 서버로 요청
            Map<String, Object> requestBody = Map.of(
                    "question", requestDto.getQuestion(),
                    "memberId", requestDto.getMemberId()
            );

            Map<String, Object> response = webClient.post()
                    .uri(llmServerUrl + "/api/chat")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();

            if (response == null || !response.containsKey("answer")) {
                throw new RuntimeException("FastAPI 서버로부터 유효한 응답을 받지 못했습니다.");
            }

            String answer = (String) response.get("answer");
            log.info("FastAPI 서버로부터 응답 수신: {}", answer);

            // 챗봇 응답 저장
            Chatbot chatbot = Chatbot.builder()
                    .memberId(requestDto.getMemberId())
                    .question(requestDto.getQuestion())
                    .answer(answer)
                    .build();

            Chatbot savedChatbot = chatbotRepository.save(chatbot);
            log.info("챗봇 응답 저장 완료: chatBotId={}", savedChatbot.getChatBotId());

            return savedChatbot.getChatBotId();

        } catch (WebClientResponseException e) {
            log.error("FastAPI 서버 통신 오류: {}", e.getResponseBodyAsString(), e);
            throw new RuntimeException("FastAPI 서버와 통신 중 오류가 발생했습니다: " + e.getMessage());
        } catch (Exception e) {
            log.error("챗봇 생성 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("챗봇 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatbotDto> getAllChatbotsByMemberId(Long memberId) {
        return chatbotRepository.findByMemberId(memberId).stream()
                .map(ChatbotDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatbotDto> getAllChatbots() {
        return chatbotRepository.findAll().stream()
                .map(ChatbotDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ChatbotDto getChatbot(Long chatBotId) {
        Chatbot chatbot = chatbotRepository.findById(chatBotId)
                .orElseThrow(() -> new IllegalArgumentException("해당 챗봇 질문이 없습니다. ID: " + chatBotId));
        return ChatbotDto.fromEntity(chatbot);
    }

    @Override
    @Transactional
    public void updateChatbotAnswer(Long chatBotId, String answer) {
        Chatbot chatbot = chatbotRepository.findById(chatBotId)
                .orElseThrow(() -> new IllegalArgumentException("해당 챗봇 질문이 없습니다. ID: " + chatBotId));
        chatbot.updateAnswer(answer);
        chatbotRepository.save(chatbot);
    }

    @Override
    @Transactional
    public void deleteChatbot(Long chatBotId) {
        if (!chatbotRepository.existsById(chatBotId)) {
            throw new IllegalArgumentException("해당 챗봇 질문이 없습니다. ID: " + chatBotId);
        }
        chatbotRepository.deleteById(chatBotId);
    }
}