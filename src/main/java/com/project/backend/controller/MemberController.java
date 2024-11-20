package com.project.backend.controller;

import com.project.backend.dto.MemberDto;
import com.project.backend.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Member", description = "회원 관리 API")
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "현재 로그인한 회원 정보 조회", description = "현재 인증된 회원의 정보를 조회합니다.")
    @GetMapping("/me")
    public ResponseEntity<MemberDto.MemberResponseDto> getCurrentMember() {
        MemberDto.MemberResponseDto member = memberService.getCurrentMember();
        return ResponseEntity.ok(member);
    }

    @Operation(summary = "회원 생성", description = "새로운 회원을 생성합니다.")
    @PostMapping
    public ResponseEntity<Long> createMember(
            @RequestBody MemberDto.MemberRequestDto requestDto) {
        // 비밀번호 암호화
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        Long memberId = memberService.createMember(requestDto);
        return ResponseEntity.ok(memberId);
    }

    @Operation(summary = "전체 회원 조회", description = "모든 회원 정보를 조회합니다. (관리자 전용)")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MemberDto.MemberResponseDto>> getAllMembers() {
        List<MemberDto.MemberResponseDto> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @Operation(summary = "회원 조회", description = "특정 회원의 정보를 조회합니다. (관리자 전용)")
    @GetMapping("/{memberId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MemberDto.MemberResponseDto> getMember(
            @Parameter(description = "회원 ID") @PathVariable Long memberId) {
        MemberDto.MemberResponseDto member = memberService.getMember(memberId);
        return ResponseEntity.ok(member);
    }

    @Operation(summary = "회원 포인트 업데이트", description = "회원의 포인트를 업데이트합니다. (관리자 전용)")
    @PatchMapping("/{memberId}/point")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateMemberPoint(
            @Parameter(description = "회원 ID") @PathVariable Long memberId,
            @Parameter(description = "변경할 포인트") @RequestParam Integer point) {
        memberService.updateMemberPoint(memberId, point);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "회원 삭제", description = "회원을 삭제합니다. (관리자 전용)")
    @DeleteMapping("/{memberId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMember(
            @Parameter(description = "회원 ID") @PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "회원 정보 수정", description = "현재 로그인한 회원의 정보를 수정합니다.")
    @PutMapping("/me")
    public ResponseEntity<MemberDto.MemberResponseDto> updateCurrentMember(
            @RequestBody MemberDto.MemberRequestDto requestDto) {
        MemberDto.MemberResponseDto updatedMember = memberService.updateCurrentMember(requestDto);
        return ResponseEntity.ok(updatedMember);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
