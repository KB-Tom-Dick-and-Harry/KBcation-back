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

    @Operation(summary = "챗봇 질문 생성", description = "새로운 챗봇 질문을 생성합니다.")
    @PostMapping
    public ResponseEntity<Long> createChatbot(
            @RequestBody ChatbotDto requestDto) {
        Long chatBotId = chatbotService.createChatbot(requestDto);
        return ResponseEntity.ok(chatBotId);
    }

    @Operation(summary = "전체 챗봇 질문 조회", description = "모든 챗봇 질문을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<ChatbotDto>> getAllChatbots() {
        List<ChatbotDto> chatbots = chatbotService.getAllChatbots();
        return ResponseEntity.ok(chatbots);
    }

    @Operation(summary = "챗봇 질문 조회", description = "특정 챗봇 질문을 조회합니다.")
    @GetMapping("/{chatBotId}")
    public ResponseEntity<ChatbotDto> getChatbot(
            @Parameter(description = "챗봇 ID") @PathVariable Long chatBotId) {
        ChatbotDto chatbot = chatbotService.getChatbot(chatBotId);
        return ResponseEntity.ok(chatbot);
    }

    @Operation(summary = "챗봇 답변 업데이트", description = "챗봇의 답변을 업데이트합니다.")
    @PatchMapping("/{chatBotId}/answer")
    public ResponseEntity<Void> updateChatbotAnswer(
            @Parameter(description = "챗봇 ID") @PathVariable Long chatBotId,
            @Parameter(description = "변경할 답변") @RequestParam String answer) {
        chatbotService.updateChatbotAnswer(chatBotId, answer);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "챗봇 질문 삭제", description = "특정 챗봇 질문을 삭제합니다.")
    @DeleteMapping("/{chatBotId}")
    public ResponseEntity<Void> deleteChatbot(
            @Parameter(description = "챗봇 ID") @PathVariable Long chatBotId) {
        chatbotService.deleteChatbot(chatBotId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
