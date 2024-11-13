package com.project.backend.service;

import com.project.backend.dto.CardDto;

import java.util.List;

public interface CardService {
    Long createCard(CardDto.CardRequestDto requestDto);
    List<CardDto.CardResponseDto> getAllCards();
    CardDto.CardResponseDto getCard(Long cardId);
    List<CardDto.CardResponseDto> getRecommendedCards(); // 추천 카드 조회 메서드 추가
}
