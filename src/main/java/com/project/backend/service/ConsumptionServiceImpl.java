package com.project.backend.service;

import com.project.backend.dto.ConsumptionDto;
import com.project.backend.model.Consumption;
import com.project.backend.repository.ConsumptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsumptionServiceImpl implements ConsumptionService {

    private final ConsumptionRepository consumptionRepository;

    @Override
    public Integer createConsumption(ConsumptionDto.ConsumptionRequestDto requestDto) {
        Consumption consumption = requestDto.toEntity();
        consumption = consumptionRepository.save(consumption);
        return consumption.getConsumptionId();
    }

    @Override
    public List<ConsumptionDto.ConsumptionResponseDto> getAllConsumption() {
        return consumptionRepository.findAll().stream()
                .map(ConsumptionDto.ConsumptionResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void updateConsumption(Integer consumptionId, ConsumptionDto.ConsumptionRequestDto requestDto) {
        Consumption consumption = consumptionRepository.findById(consumptionId)
                .orElseThrow() -> new IllegalArgumentException("소비 내역을 찾을 수 없습니다.");
        consumption.updateDetails(requestDto);
        consumptionRepository.save(consumption);
    }

    @Override
    public void deleteConsumption(Integer consumptionId) {
        if (!consumptionRepository.existsById(consumptionId)) {
            throw new IllegalArgumentException("소비 내역을 찾을 수 없습니다.");
        }
        consumptionRepository.deleteById(consumptionId);
    }
}
