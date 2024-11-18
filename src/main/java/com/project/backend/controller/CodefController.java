package com.project.backend.controller;

import com.project.backend.dto.MemberDto;
import com.project.backend.service.CodefService;
import com.project.backend.service.ConsumptionServiceImpl;
import com.project.backend.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/codef")
@RequiredArgsConstructor
public class CodefController {

    private final MemberServiceImpl memberService;
    private final CodefService codefService;
    private final ConsumptionServiceImpl consumptionServiceImpl;

    // CODEF Connected ID 생성 후 저장
    @PostMapping("/connectedId/{memberId}")
    public ResponseEntity<MemberDto.MemberResponseDto> createConnectedId(
            @PathVariable Long memberId,
            @RequestBody Map<String, String> accountInfo) {
        MemberDto.MemberResponseDto memberResponseDto = memberService.connectAccount(memberId, accountInfo);
        return ResponseEntity.ok(memberResponseDto);
    }

}
