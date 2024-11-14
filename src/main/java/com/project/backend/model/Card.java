package com.project.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Column(length = 50, nullable = false)
    private String cardName;

    @Column(length = 100)
    private String description;

    @Builder
    public Card(String cardName, String description) {
        this.cardName = cardName;
        this.description = description;
    }
}
