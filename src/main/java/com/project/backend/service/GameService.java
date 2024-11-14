package com.project.backend.service;

import com.project.backend.dto.GameDto;

public interface GameService {
    GameDto.GameResponseDto startNewGame(Long memberId);
    GameDto.GameResponseDto submitAnswer(Long gameId, String yourAnswer);
    GameDto.GameResponseDto createNextGame(Long previousGameId);
}
