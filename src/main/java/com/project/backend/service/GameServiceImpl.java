package com.project.backend.service;

import com.project.backend.dto.GameDto;
import com.project.backend.model.Game;
import com.project.backend.model.Member;
import com.project.backend.repository.GameRepository;
import com.project.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public GameDto.GameResponseDto startNewGame(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 새로운 1단계 게임 생성
        Game game = Game.builder()
                .member(member)
                .gameRound(1)
                .quiz(generateQuizForRound())
                .answerOptions(generateAnswerOptions())
                .correctAnswer(generateCorrectAnswer())
                .answerExplanation(generateAnswerExplanation())
                .build();

        gameRepository.save(game);

        // 생성된 게임을 GameResponseDto로 변환하여 반환
        return new GameDto.GameResponseDto(game);
    }

    @Override
    @Transactional
    public GameDto.GameResponseDto submitAnswer(Long gameId, String yourAnswer) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));

        // 사용자가 선택한 답변 저장
        game.setYourAnswer(yourAnswer);

        // 게임 상태를 업데이트한 후 응답 반환
        return new GameDto.GameResponseDto(game);
    }

    @Override
    @Transactional
    public GameDto.GameResponseDto createNextGame(Long previousGameId) {
        Game previousGame = gameRepository.findById(previousGameId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임입니다."));

        // 정답 여부 확인
        if (!previousGame.getYourAnswer().equals(previousGame.getCorrectAnswer())) {
            throw new IllegalStateException("정답이 맞지 않아 다음 단계로 진행할 수 없습니다.");
        }

        // 다음 단계 생성
        int nextRound = previousGame.getGameRound() + 1;
        if (nextRound > 5) {
            throw new IllegalStateException("모든 단계를 완료했습니다.");
        }

        // 새로운 단계의 게임 생성
        Game nextGame = Game.builder()
                .member(previousGame.getMember())
                .gameRound(nextRound)
                .quiz(generateQuizForRound())
                .answerOptions(generateAnswerOptions())
                .correctAnswer(generateCorrectAnswer())
                .answerExplanation(generateAnswerExplanation())
                .build();

        gameRepository.save(nextGame);

        return new GameDto.GameResponseDto(nextGame);
    }

    private String generateQuizForRound() {
        // 각 단계에 따른 퀴즈 생성 로직 구현
        return "태웅이의 성은 무엇인가요?";
    }

    private List<String> generateAnswerOptions() {
        // 보기 4개 생성 로직 구현
        return List.of("김씨", "이씨", "박씨", "최씨");
    }

    private String generateCorrectAnswer() {
        // 단계에 따른 정답 생성 로직 구현
        return "이씨";
    }

    private String generateAnswerExplanation() {
        // 정답에 대한 해설 생성 로직 구현
        return "해설";
    }
}
