package com.project.backend.service;

import com.project.backend.dto.CardDto;

import java.util.List;
import java.util.Map; // Map을 임포트

public interface CardService {
    Long createCard(CardDto.CardRequestDto requestDto);
    List<CardDto.CardResponseDto> getAllCards();
    CardDto.CardResponseDto getCard(Long cardId);
    List<CardDto.CardResponseDto> getRecommendedCards();

    // 추가: 사용자 자산 정보를 기반으로 카드 추천을 업데이트하는 메서드
    List<CardDto.CardResponseDto> updateRecommendations(Map<String, Object> assetInfo);
}
