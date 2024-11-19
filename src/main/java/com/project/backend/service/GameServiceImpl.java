package com.project.backend.service;

import com.project.backend.dto.GameDto;
import com.project.backend.entity.MemberEntity;
import com.project.backend.model.Game;
import com.project.backend.model.Member;
import com.project.backend.repository.GameRepository;
import com.project.backend.repository.MemberRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final MemberRepository memberRepository;
    private final WebClient webClient;

    @Value("${game.server.url}")
    private String gameServerUrl;

    @Data
    private static class GameQuestion {
        private String quiz;
        private List<String> answer_options;
        private String correct_answer;
        private String answer_explanation;
    }

    @Data
    private static class GameResponse {
        private boolean success;
        private GameQuestion data;
        private String error;
    }

    @Override
    @Transactional
    public GameDto.GameResponseDto startNewGame(Long memberId) {
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        GameQuestion gameQuestion = webClient.post()
                .uri(gameServerUrl + "/api/game/generate")
                .retrieve()
                .bodyToMono(GameResponse.class)
                .map(response -> {
                    if (!response.isSuccess()) {
                        throw new RuntimeException("게임 생성 실패: " + response.getError());
                    }
                    return response.getData();
                })
                .block();

        Game game = Game.builder()
                .member(member)
                .gameRound(1)
                .quiz(gameQuestion.getQuiz())
                .answerOptions(gameQuestion.getAnswer_options())
                .correctAnswer(gameQuestion.getCorrect_answer())
                .answerExplanation(gameQuestion.getAnswer_explanation())
                .build();

        gameRepository.save(game);
        return new GameDto.GameResponseDto(game);
    }

    @Override
    @Transactional
    public GameDto.GameResponseDto submitAnswer(Long gameId, String yourAnswer) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));
        game.setYourAnswer(yourAnswer);
        return new GameDto.GameResponseDto(game);
    }

    @Override
    @Transactional
    public GameDto.GameResponseDto createNextGame(Long previousGameId) {
        Game previousGame = gameRepository.findById(previousGameId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));

        if (!previousGame.isCorrect()) {
            throw new IllegalStateException("정답이 맞지 않아 다음 단계로 진행할 수 없습니다.");
        }

        int nextRound = previousGame.getGameRound() + 1;
        if (nextRound > 5) {
            throw new IllegalStateException("모든 단계를 완료했습니다.");
        }

        GameQuestion gameQuestion = webClient.post()
                .uri(gameServerUrl + "/api/game/generate")
                .retrieve()
                .bodyToMono(GameResponse.class)
                .map(response -> {
                    if (!response.isSuccess()) {
                        throw new RuntimeException("게임 생성 실패: " + response.getError());
                    }
                    return response.getData();
                })
                .block();

        Game nextGame = Game.builder()
                .member(previousGame.getMember())
                .gameRound(nextRound)
                .quiz(gameQuestion.getQuiz())
                .answerOptions(gameQuestion.getAnswer_options())
                .correctAnswer(gameQuestion.getCorrect_answer())
                .answerExplanation(gameQuestion.getAnswer_explanation())
                .build();

        gameRepository.save(nextGame);
        return new GameDto.GameResponseDto(nextGame);
    }
}
