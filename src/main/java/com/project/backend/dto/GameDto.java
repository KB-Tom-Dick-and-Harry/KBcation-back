package com.project.backend.dto;

import com.project.backend.model.Game;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class GameDto {

    // 요청 DTO
    @Getter
    @NoArgsConstructor
    public static class GameRequestDto {
        private Long memberId;
        private String yourAnswer;

        @Builder
        public GameRequestDto(Long memberId, String yourAnswer) {
            this.memberId = memberId;
            this.yourAnswer = yourAnswer;
        }
    }

    // 응답 DTO
    @Getter
    public static class GameResponseDto {
        private Long gameId;
        private Integer gameRound;
        private String quiz;
        private List<String> answerOptions;
        private String correctAnswer;
        private String yourAnswer;
        private String  answerExplanation;

        public GameResponseDto(Game game) {
            this.gameId = game.getGameId();
            this.gameRound = game.getGameRound();
            this.quiz = game.getQuiz();
            this.answerOptions = game.getAnswerOptions();
            this.correctAnswer = game.getCorrectAnswer();
            this.yourAnswer = game.getYourAnswer();
            this.answerExplanation = game.getAnswerExplanation();
        }
    }
}
