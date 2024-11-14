package com.project.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chatbot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatBotId;

    @Column(nullable = false)
    private Long memberId;

    @Column(length = 1000)
    private String question;

    @Column(columnDefinition = "TEXT")
    private String answer;

    @Builder
    public Chatbot(Long memberId, String question, String answer) {
        this.memberId = memberId;
        this.question = question;
        this.answer = answer;
    }

    public void updateAnswer(String answer) {
        this.answer = answer;
    }
}