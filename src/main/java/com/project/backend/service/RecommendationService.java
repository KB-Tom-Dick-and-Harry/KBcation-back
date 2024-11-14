package com.project.backend.service;

import com.project.backend.dto.RecommendationDto;

import java.util.List;

public interface RecommendationService {
    Long createRecommendation(RecommendationDto.RecommendationRequestDto requestDto);
    List<RecommendationDto.RecommendationResponseDto> getAllRecommendations();
    RecommendationDto.RecommendationResponseDto getRecommendation(Long recommendationId);
}
