package com.project.backend.controller;

import com.project.backend.dto.AccountInfoDto;
import com.project.backend.dto.ConsumptionDto;
import com.project.backend.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Transaction",description = "거래 내역 업로드 API")
@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionsController {

    private final MemberServiceImpl memberService;
    private final CodefService codefService;
    private final ConsumptionServiceImpl consumptionServiceImpl;
    private final AccountInfoServiceImpl accountInfoService;

    // CODEF Connected ID 생성 후 저장
    @Operation(summary = "금융 계좌 연동")
    @PostMapping("/create/accountInfo/{memberId}")
    public ResponseEntity<Map<String, Object>> connectAccount(
            @PathVariable Long memberId,
            @RequestBody AccountInfoDto.AccountInfoRequestDto requestDto) {
        // connectedId 생성
        String connectedId = codefService.registerAccount(memberId, requestDto);

        // accountInfo 저장
        AccountInfoDto.AccountInfoResponseDto accountInfo = accountInfoService.saveAccountInfo(memberId, connectedId, requestDto);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String today = sdf.format(new Date());
        String oneMonthAgo = sdf.format(new Date(System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000));

        // 1개월치 데이터 조회 및 저장
        String responseJson = codefService.getTransactionList(accountInfo, oneMonthAgo, today);
        int currentBalance = consumptionServiceImpl.saveTransactionData(responseJson, memberId);

        // 최신 3건 거래내역 조회
        List<ConsumptionDto.ConsumptionResponseDto> recentTransactions = consumptionServiceImpl.getRecentTransactions(memberId);

        // 응답 데이터 생성
        Map<String, Object> response = new HashMap<>();
        response.put("currentBalance", currentBalance);
        response.put("recentTransactions", recentTransactions);

        return ResponseEntity.ok(response);
    }

    // 최신 거래내역 업데이트 및 조회
    @Operation(summary = "최신 거래내역 업데이트")
    @PostMapping("/update/{memberId}")
    public ResponseEntity<Map<String, Object>> updateAndFetchRecentTransactions(
            @PathVariable Long memberId) {
        // AccountInfo 데이터 조회
        AccountInfoDto.AccountInfoResponseDto accountInfo = accountInfoService.getAccountInfo(memberId);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String today = sdf.format(new Date());

        // 당일 거래내역 조회 및 저장
        String responseJson = codefService.getTransactionList(accountInfo, today, today);
        int currentBalance = consumptionServiceImpl.saveTransactionData(responseJson, memberId);

        // 최신 3건 거래내역 조회
        List<ConsumptionDto.ConsumptionResponseDto> recentTransactions = consumptionServiceImpl.getRecentTransactions(memberId);

        // 응답 데이터 생성
        Map<String, Object> response = new HashMap<>();
        response.put("currentBalance", currentBalance);
        response.put("recentTransactions", recentTransactions);

        return ResponseEntity.ok(response);
    }

    // 6개월치 거래내역 저장 컨트롤러 메서드
    @Operation(summary = "6개월 거래내역 저장")
    @PostMapping("/save/six-months/{memberId}")
    public ResponseEntity<String> saveSixMonthsTransactionData(@PathVariable Long memberId) {
        // AccountInfo 데이터 조회
        AccountInfoDto.AccountInfoResponseDto accountInfo = accountInfoService.getAccountInfo(memberId);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        // 현재 날짜와 6개월 전 날짜 계산
        String today = sdf.format(new Date());
        String sixMonthsAgo = sdf.format(new Date(System.currentTimeMillis() - 180L * 24 * 60 * 60 * 1000));

        // 6개월치 데이터 조회 및 저장
        String responseJson = codefService.getTransactionList(accountInfo, sixMonthsAgo, today);
        consumptionServiceImpl.saveTransactionData(responseJson, memberId);

        return ResponseEntity.ok("6개월치 거래내역이 저장되었습니다.");
    }
}
