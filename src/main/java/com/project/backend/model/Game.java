package com.project.backend.model;

import com.project.backend.model.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;
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

//    private String option1;
//    private String option2;
//    private String option3;
//    private String option4;

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
//        this.option1 = answerOptions.get(0);
//        this.option2 = answerOptions.get(1);
//        this.option3 = answerOptions.get(2);
//        this.option4 = answerOptions.get(3);
        this.correctAnswer = correctAnswer;
        this.answerExplanation = answerExplanation;
        this.isCorrect = false;
    }

//    public List<String> getAnswerOptions() {
//        return Arrays.asList(option1, option2, option3, option4);
//    }

    public void setYourAnswer(String yourAnswer) {
        this.yourAnswer = yourAnswer;
        this.isCorrect = this.correctAnswer.equals(yourAnswer);
    }

}
