package com.project.backend.service;

import com.project.backend.dto.ConsumptionDto;
import java.util.List;

public interface ConsumptionService {

    //소비 내역 생성
    Integer createConsumption(ConsumptionDto.ConsumptionRequestDto requestDto);

    //전체 소비 내역 조회
    List<ConsumptionDto.ConsumptionResponseDto> getAllConsumption();

    //특정 소비 내역 조회
    ConsumptionDto.ConsumptionResponseDto getConsumption(Integer consumptionId);

    //특정 회원의 소비 내역 조회
    List<ConsumptionDto.ConsumptionResponseDto> getConsumptionsByMemberId(Integer memberId);

    //소비 내역 수정
    void updateConsumption(Integer consumptionId, ConsumptionDto.ConsumptionRequestDto requestDto);

    //소비 내역 삭제
    void deleteConsumption(Integer consumptionId);
}
