package com.project.backend.controller;

import com.project.backend.dto.ChatbotDto;
import com.project.backend.dto.ChatbotDto;
import com.project.backend.service.ChatbotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Chatbot", description = "챗봇 관리 API")
@RestController
@RequestMapping("/api/chatbots")
@RequiredArgsConstructor
public class ChatbotController {

    private final ChatbotService chatbotService;

    @Operation(summary = "챗봇에게 질문하기", description = "챗봇에게 질문을 하고 AI 응답을 받습니다.")
    @PostMapping("/ask")
    public ResponseEntity<ChatbotDto> askChatbot(@RequestBody ChatbotDto requestDto) {
        Long chatBotId = chatbotService.createChatbot(requestDto);
        ChatbotDto response = chatbotService.getChatbot(chatBotId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "사용자별 채팅 기록 조회", description = "특정 사용자의 모든 채팅 기록을 조회합니다.")
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<ChatbotDto>> getMemberChatHistory(
            @Parameter(description = "회원 ID") @PathVariable Long memberId) {
        List<ChatbotDto> chatHistory = chatbotService.getAllChatbotsByMemberId(memberId);
        return ResponseEntity.ok(chatHistory);
    }

    @Operation(summary = "전체 채팅 기록 조회", description = "모든 채팅 기록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<ChatbotDto>> getAllChatbots() {
        List<ChatbotDto> chatbots = chatbotService.getAllChatbots();
        return ResponseEntity.ok(chatbots);
    }

    @Operation(summary = "특정 채팅 조회", description = "특정 채팅 내용을 조회합니다.")
    @GetMapping("/{chatBotId}")
    public ResponseEntity<ChatbotDto> getChatbot(
            @Parameter(description = "챗봇 대화 ID") @PathVariable Long chatBotId) {
        ChatbotDto chatbot = chatbotService.getChatbot(chatBotId);
        return ResponseEntity.ok(chatbot);
    }

    @Operation(summary = "챗봇 답변 수동 수정", description = "챗봇의 답변을 수동으로 수정합니다.")
    @PatchMapping("/{chatBotId}/answer")
    public ResponseEntity<Void> updateChatbotAnswer(
            @Parameter(description = "챗봇 대화 ID") @PathVariable Long chatBotId,
            @Parameter(description = "수정할 답변") @RequestParam String answer) {
        chatbotService.updateChatbotAnswer(chatBotId, answer);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "채팅 기록 삭제", description = "특정 채팅 기록을 삭제합니다.")
    @DeleteMapping("/{chatBotId}")
    public ResponseEntity<Void> deleteChatbot(
            @Parameter(description = "챗봇 대화 ID") @PathVariable Long chatBotId) {
        chatbotService.deleteChatbot(chatBotId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
}