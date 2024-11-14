package com.project.backend.dto;

import com.project.backend.model.Card;
import com.project.backend.model.Recommendation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RecommendationDto {

    @Getter
    @NoArgsConstructor
    public static class RecommendationRequestDto {
        private Long cardId;
        private String recommendationDetails;

        @Builder
        public RecommendationRequestDto(Long cardId, String recommendationDetails) {
            this.cardId = cardId;
            this.recommendationDetails = recommendationDetails;
        }

        public Recommendation toEntity(Card card) {
            return Recommendation.builder()
                    .card(card)
                    .recommendationDetails(recommendationDetails)
                    .build();
        }
    }

    @Getter
    public static class RecommendationResponseDto {
        private Long recommendationId;
        private Long cardId;
        private String recommendationDetails;

        public RecommendationResponseDto(Recommendation recommendation) {
            this.recommendationId = recommendation.getRecommendationId();
            this.cardId = recommendation.getCard().getCardId();
            this.recommendationDetails = recommendation.getRecommendationDetails();
        }
    }
}
