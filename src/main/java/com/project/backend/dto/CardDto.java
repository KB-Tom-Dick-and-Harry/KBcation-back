package com.project.backend.dto;

import com.project.backend.model.Card;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CardDto {

    @Getter
    @NoArgsConstructor
    public static class CardRequestDto {
        private String cardName;
        private String description;

        @Builder
        public CardRequestDto(String cardName, String description) {
            this.cardName = cardName;
            this.description = description;
        }

        public Card toEntity() {
            return Card.builder()
                    .cardName(cardName)
                    .description(description)
                    .build();
        }
    }

    @Getter
    public static class CardResponseDto {
        private Long cardId;
        private String cardName;
        private String description;

        public CardResponseDto(Card card) {
            this.cardId = card.getCardId();
            this.cardName = card.getCardName();
            this.description = card.getDescription();
        }

        // 새로운 생성자 추가
        public CardResponseDto(Long cardId, String cardName, String description) {
            this.cardId = cardId;
            this.cardName = cardName;
            this.description = description;
        }

        // 정적 팩토리 메서드 추가 (선택적)
        public static CardResponseDto of(Long cardId, String cardName, String description) {
            return new CardResponseDto(cardId, cardName, description);
        }
    }
}