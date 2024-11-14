package com.project.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommendationId;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    private String recommendationDetails;

    @Builder
    public Recommendation(Card card, String recommendationDetails) {
        this.card = card;
        this.recommendationDetails = recommendationDetails;
    }
}
