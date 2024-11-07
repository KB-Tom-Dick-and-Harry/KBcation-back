package com.project.backend.controller;


import com.project.backend.dto.ConsumptionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Consumption",description = "소비 내역 관리 API")
@RestController
@RequestMapping("/api/consumptions")
@RequiredArgsConstructor
public class ConsumptionController {

    private final ConsumptionService consumptionService;

    @Operation(summary = "소비 내역 생성", description = "새로운 소비 내역을 생성합니다.")
    @PostMapping
    public ResponseEntity<Integer> createConsumption(
            @RequestBody ConsumptionDto.ConsumptionRequestDto requestDto) {
        Integer consumptionId = consumptionService.createConsumption(requestDto);
        return ResponseEntity.ok(consumptionId);
    }

    @Operation(summary = "전체 소비 내역 조회", description = "모든 소비 내역을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<ConsumptionDto.ConsumptionResponseDto>> getAllConsumption() {
        List<ConsumptionDto.ConsumptionResponseDto> consumptions = consumptionService.getAllConsumptions();
        return ResponseEntity.ok(consumptions);
    }

    @Operation(summary = "소비 내역 조회", description = "특정 소비 내역을 조회합니다.")
    @GetMapping("/{consumptionId}")
    public ResponseEntity<ConsumptionDto.ConsumptionResponseDto> getConsumptionById(
            @Parameter(description = "소비 내역 ID") @PathVariable Integer consumptionId) {
        ConsumptionDto.ConsumptionResponseDto consumption = consumptionService.getConsumption(consumptionId);
        return ResponseEntity.ok(consumption);
    }

    @Operation(summary = "회원별 소비 내역 조회",description = "특정 회원의 소비 내역을 조회합니다.")
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<ConsumptionDto.ConsumptionResponseDto>> getConsumptionsByMemberId(
            @Parameter(description = "회원 ID") @PathVariable Integer memberId) {
        List<ConsumptionDto.ConsumptionResponseDto> consumptions = consumptionService.getConsumptionsByMemberId(memberId);
        return ResponseEntity.ok(consumptions);
    }

    @Operation(summary = "소비 내역 수정", description = "소비 내역의 상세 정보를 수정합니다.")
    @PatchMapping("/{consumptionId}")
    public ResponseEntity<Void> updateConsumptionDetails(
            @Parameter(description = "소비 내역 ID") @PathVariable Integer consumptionId,
            @RequestBody ConsumptionDto.ConsumptionRequestDto requestDto) {
        consumptionService.updateConsumption(consumptionId, requestDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "소비 내역 삭제", description = "소비 내역을 삭제합니다.")
    @DeleteMapping("/{consumptionId}")
    public ResponseEntity<Void> deleteConsumption(
            @Parameter(description = "소비 내역 ID") @PathVariable Integer consumptionId) {
        consumptionService.deleteConsumption(consumptionId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
