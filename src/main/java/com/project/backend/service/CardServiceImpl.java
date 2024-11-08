package com.project.backend.service.impl;

import com.project.backend.dto.CardDto;
import com.project.backend.model.Card;
import com.project.backend.repository.CardRepository;
import com.project.backend.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    @Override
    @Transactional
    public Long createCard(CardDto.CardRequestDto requestDto) {
        Card card = cardRepository.save(requestDto.toEntity());
        return card.getCardId();
    }

    @Override
    public List<CardDto.CardResponseDto> getAllCards() {
        return cardRepository.findAll().stream()
                .map(CardDto.CardResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public CardDto.CardResponseDto getCard(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));
        return new CardDto.CardResponseDto(card);
    }
}
