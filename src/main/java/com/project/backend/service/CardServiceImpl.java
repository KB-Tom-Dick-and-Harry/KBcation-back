package com.project.backend.service;

import com.project.backend.dto.CardDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Override
    public Long createCard(CardDto.CardRequestDto requestDto) {
        // 카드 생성 로직
        // TODO: 실제 데이터베이스에 카드를 저장하는 로직 구현
        return 1L; // 생성된 카드 ID 반환 (예시)
    }

    @Override
    public List<CardDto.CardResponseDto> getAllCards() {
        // 모든 카드 조회 로직
        // TODO: 실제 데이터베이스에서 모든 카드를 조회하는 로직 구현
        List<CardDto.CardResponseDto> allCards = new ArrayList<>();
        allCards.add(new CardDto.CardResponseDto(1L, "Card 1", "Description 1"));
        allCards.add(new CardDto.CardResponseDto(2L, "Card 2", "Description 2"));
        return allCards;
    }

    @Override
    public CardDto.CardResponseDto getCard(Long cardId) {
        // 특정 카드 조회 로직
        // TODO: 실제 데이터베이스에서 특정 ID의 카드를 조회하는 로직 구현
        return new CardDto.CardResponseDto(cardId, "Example Card", "Description");
    }

    @Override
    public List<CardDto.CardResponseDto> getRecommendedCards() {
        // 추천 카드 조회 로직
        // TODO: 실제 추천 알고리즘을 구현하여 카드를 추천하는 로직 구현
        List<CardDto.CardResponseDto> recommendedCards = new ArrayList<>();
        recommendedCards.add(new CardDto.CardResponseDto(1L, "Recommended Card 1", "Description 1"));
        recommendedCards.add(new CardDto.CardResponseDto(2L, "Recommended Card 2", "Description 2"));
        return recommendedCards;
    }
}