package com.project.backend.service;

import com.project.backend.dto.CardDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map; // Map을 임포트

@Service
public class CardServiceImpl implements CardService {

    @Override
    public Long createCard(CardDto.CardRequestDto requestDto) {
        return 1L; // 예시 ID 반환
    }

    @Override
    public List<CardDto.CardResponseDto> getAllCards() {
        List<CardDto.CardResponseDto> allCards = new ArrayList<>();
        allCards.add(new CardDto.CardResponseDto(1L, "Card 1", "Description 1"));
        allCards.add(new CardDto.CardResponseDto(2L, "Card 2", "Description 2"));
        return allCards;
    }

    @Override
    public CardDto.CardResponseDto getCard(Long cardId) {
        return new CardDto.CardResponseDto(cardId, "Example Card", "Description");
    }

    @Override
    public List<CardDto.CardResponseDto> getRecommendedCards() {
        List<CardDto.CardResponseDto> recommendedCards = new ArrayList<>();
        recommendedCards.add(new CardDto.CardResponseDto(1L, "Recommended Card 1", "Description 1"));
        recommendedCards.add(new CardDto.CardResponseDto(2L, "Recommended Card 2", "Description 2"));
        return recommendedCards;
    }

    @Override
    public List<CardDto.CardResponseDto> updateRecommendations(Map<String, Object> assetInfo) {
        List<CardDto.CardResponseDto> updatedRecommendations = new ArrayList<>();

        // 예시 추천 로직
        if (assetInfo.containsKey("spendingCategory") && assetInfo.get("spendingCategory").equals("shopping")) {
            updatedRecommendations.add(new CardDto.CardResponseDto(1L, "Shopping Card", "Best for shopping"));
        } else {
            updatedRecommendations.add(new CardDto.CardResponseDto(2L, "General Card", "Suitable for general use"));
        }

        return updatedRecommendations;
    }
}
