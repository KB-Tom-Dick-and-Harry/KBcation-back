package com.project.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.backend.dto.ConsumptionDto;
import com.project.backend.model.Consumption;
import com.project.backend.repository.ConsumptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
                .orElseThrow(() -> new IllegalArgumentException("소비 내역을 찾을 수 없습니다."));
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

    @Override
    public ConsumptionDto.ConsumptionResponseDto getConsumption(Integer consumptionId) {
        Consumption consumption = consumptionRepository.findById(consumptionId)
                .orElseThrow(() -> new IllegalArgumentException("소비 내역을 찾을 수 없습니다."));
        return new ConsumptionDto.ConsumptionResponseDto(consumption);
    }

    @Override
    public List<ConsumptionDto.ConsumptionResponseDto> getConsumptionsByMemberId(Long memberId) {
        return consumptionRepository.findByMemberId(memberId).stream()
                .map(ConsumptionDto.ConsumptionResponseDto::new)
                .collect(Collectors.toList());
    }

    // 거래내역 저장 및 현재 잔액 반환
    public int saveTransactionData(String responseJson, Long memberId) {
        int currentBalance = 0;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseJson);

            // 'data' 필드 추출
            JsonNode dataNode = rootNode.get("data");
            if (dataNode == null || dataNode.isNull()) {
                throw new RuntimeException("Response JSON에 'data' 필드가 없습니다.");
            }

            // 현재 잔액 추출
            JsonNode accountBalanceNode = dataNode.get("resAccountBalance");
            if (accountBalanceNode != null && !accountBalanceNode.asText().isEmpty()) {
                currentBalance = Integer.parseInt(accountBalanceNode.asText());
            } else {
                throw new RuntimeException("현재 잔액(resAccountBalance) 조회 실패");
            }

            // 거래내역 리스트 추출
            JsonNode transactionList = dataNode.get("resTrHistoryList");

            // 거래내역 처리
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            for (JsonNode transaction : transactionList) {
                // 입금 내역은 저장하지 않음 (resAccountIn > 0인 경우 제외)
                int accountIn = Integer.parseInt(transaction.get("resAccountIn").asText());
                if (accountIn > 0) {
                    continue;
                }

                // 출금 내역만 저장 (resAccountOut > 0)
                int accountOut = Integer.parseInt(transaction.get("resAccountOut").asText());
                if (accountOut > 0) {
                    Date transactionDate = dateFormat.parse(transaction.get("resAccountTrDate").asText());
                    String details = transaction.get("resAccountDesc2").asText() + " " +
                            transaction.get("resAccountDesc3").asText();

                    if (!consumptionRepository.existsByMemberIdAndDateAndSpendingAmountAndConsumptionDetails(memberId, transactionDate, accountOut, details)) {
                        ConsumptionDto.ConsumptionRequestDto dto = ConsumptionDto.ConsumptionRequestDto.builder()
                                .memberId(memberId)
                                .consumptionDetails(details)
                                .category("BANK_TRANSACTION")
                                .spendingAmount(accountOut)
                                .date(transactionDate)
                                .currentBalance(currentBalance)
                                .build();
                        consumptionRepository.save(dto.toEntity());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("거래내역 저장 실패", e);
        }

        return currentBalance;
    }

    public List<ConsumptionDto.ConsumptionResponseDto> getRecentTransactions(Long memberId) {
        return consumptionRepository.findTop3ByMemberIdOrderByDateDesc(memberId).stream()
                .map(ConsumptionDto.ConsumptionResponseDto::new)
                .collect(Collectors.toList());
    }

    public Integer getLatestBalance(Long memberId) {
        return consumptionRepository.findTopByMemberIdOrderByDateDesc(memberId)
                .map(Consumption::getCurrentBalance)
                .orElse(0);
    }
}

