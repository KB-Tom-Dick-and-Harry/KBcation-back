package com.project.backend.service;

import com.project.backend.dto.CardDto;

import java.util.List;

public interface CardService {
    Long createCard(CardDto.CardRequestDto requestDto);
    List<CardDto.CardResponseDto> getAllCards();
    CardDto.CardResponseDto getCard(Long cardId);
}
