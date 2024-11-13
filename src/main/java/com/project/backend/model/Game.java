package com.project.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    private Integer gameRound;
    private String quiz;

    @ElementCollection
    private List<String> answerOptions;

    private String correctAnswer;
    private String yourAnswer;
    private String answerExplanation;

    private boolean isCorrect;

    @Builder
    public Game(Member member, Integer gameRound, String quiz, List<String> answerOptions, String correctAnswer, String answerExplanation, boolean isCorrect) {
        this.member = member;
        this.gameRound = gameRound;
        this.quiz = quiz;
        this.answerOptions = answerOptions;
        this.correctAnswer = correctAnswer;
        this.answerExplanation = answerExplanation;
        this.isCorrect = false;
    }

    public void setYourAnswer(String yourAnswer) {
        this.yourAnswer = yourAnswer;
        this.isCorrect = this.correctAnswer.equals(yourAnswer);
    }

}
