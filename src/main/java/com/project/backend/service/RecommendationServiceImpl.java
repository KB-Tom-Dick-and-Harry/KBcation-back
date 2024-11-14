package com.project.backend.service.impl;

import com.project.backend.dto.RecommendationDto;
import com.project.backend.model.Card;
import com.project.backend.model.Recommendation;
import com.project.backend.repository.CardRepository;
import com.project.backend.repository.RecommendationRepository;
import com.project.backend.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final CardRepository cardRepository;

    @Override
    @Transactional
    public Long createRecommendation(RecommendationDto.RecommendationRequestDto requestDto) {
        Card card = cardRepository.findById(requestDto.getCardId())
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));
        Recommendation recommendation = recommendationRepository.save(requestDto.toEntity(card));
        return recommendation.getRecommendationId();
    }

    @Override
    public List<RecommendationDto.RecommendationResponseDto> getAllRecommendations() {
        return recommendationRepository.findAll().stream()
                .map(RecommendationDto.RecommendationResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public RecommendationDto.RecommendationResponseDto getRecommendation(Long recommendationId) {
        Recommendation recommendation = recommendationRepository.findById(recommendationId)
                .orElseThrow(() -> new IllegalArgumentException("Recommendation not found"));
        return new RecommendationDto.RecommendationResponseDto(recommendation);
    }
}
