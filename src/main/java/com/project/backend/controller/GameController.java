package com.project.backend.controller;

import com.project.backend.dto.GameDto;
import com.project.backend.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Game", description = "게임 관리 API")
@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @Operation(summary = "게임 시작하기", description = "1단계 게임을 생성하고 정보를 반환합니다.")
    @PostMapping("/start")
    public ResponseEntity<GameDto.GameResponseDto> startGame(@RequestParam Long memberId) {
        GameDto.GameResponseDto gameResponse = gameService.startNewGame(memberId);
        return ResponseEntity.ok(gameResponse);
    }

    @Operation(summary = "답변 제출", description = "사용자가 선택한 답변을 제출합니다.")
    @PostMapping("/{gameId}/submit")
    public ResponseEntity<GameDto.GameResponseDto> submitAnswer(
            @PathVariable Long gameId,
            @RequestBody GameDto.GameRequestDto requestDto) {
        GameDto.GameResponseDto response = gameService.submitAnswer(gameId, requestDto.getYourAnswer());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "다음 단계로 진행", description = "정답을 맞춘 경우 다음 단계로 진행합니다.")
    @PostMapping("/{gameId}/next")
    public ResponseEntity<Long> nextGame(@PathVariable Long gameId) {
        Long nextGameId = gameService.createNextGame(gameId);
        return ResponseEntity.ok(nextGameId);
    }
}
