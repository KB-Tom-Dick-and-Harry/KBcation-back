package com.project.backend.controller;

import com.project.backend.dto.CardDto;
import com.project.backend.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @Operation(summary = "카드 생성")
    @PostMapping
    public ResponseEntity<Long> createCard(@RequestBody CardDto.CardRequestDto requestDto) {
        return ResponseEntity.ok(cardService.createCard(requestDto));
    }

    @Operation(summary = "모든 카드 조회")
    @GetMapping
    public ResponseEntity<List<CardDto.CardResponseDto>> getAllCards() {
        return ResponseEntity.ok(cardService.getAllCards());
    }

    @Operation(summary = "카드 조회")
    @GetMapping("/{cardId}")
    public ResponseEntity<CardDto.CardResponseDto> getCard(@PathVariable Long cardId) {
        return ResponseEntity.ok(cardService.getCard(cardId));
    }

    // 새로운 추천 엔드포인트 추가
    @Operation(summary = "카드 추천")
    @GetMapping("/recommendations")
    public ResponseEntity<List<CardDto.CardResponseDto>> getRecommendedCards() {
        return ResponseEntity.ok(cardService.getRecommendedCards());
    }
}