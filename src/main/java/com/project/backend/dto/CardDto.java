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
    }
}
