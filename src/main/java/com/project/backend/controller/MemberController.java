package com.project.backend.controller;

import com.project.backend.dto.MemberDto;
import com.project.backend.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @Operation(summary = "회원 생성", description = "새로운 회원을 생성합니다.")
    @PostMapping
    public ResponseEntity<Long> createMember(
            @RequestBody MemberDto.MemberRequestDto requestDto) {
        Long memberId = memberService.createMember(requestDto);
        return ResponseEntity.ok(memberId);
    }

    @Operation(summary = "전체 회원 조회", description = "모든 회원 정보를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<MemberDto.MemberResponseDto>> getAllMembers() {
        List<MemberDto.MemberResponseDto> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @Operation(summary = "회원 조회", description = "특정 회원의 정보를 조회합니다.")
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDto.MemberResponseDto> getMember(
            @Parameter(description = "회원 ID") @PathVariable Long memberId) {
        MemberDto.MemberResponseDto member = memberService.getMember(memberId);
        return ResponseEntity.ok(member);
    }

    @Operation(summary = "회원 포인트 업데이트", description = "회원의 포인트를 업데이트합니다.")
    @PatchMapping("/{memberId}/point")
    public ResponseEntity<Void> updateMemberPoint(
            @Parameter(description = "회원 ID") @PathVariable Long memberId,
            @Parameter(description = "변경할 포인트") @RequestParam Integer point) {
        memberService.updateMemberPoint(memberId, point);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "회원 삭제", description = "회원을 삭제합니다.")
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(
            @Parameter(description = "회원 ID") @PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @PostMapping("/member/save")
    public String saveMember(
            @RequestParam("memberEmail") String email,
            @RequestParam("memberPassword") String password,
            @RequestParam("memberName") String name,
            @RequestParam(value = "gender", defaultValue = "unknown") String gender,
            @RequestParam(value = "birth", defaultValue = "unknown") String birth
    ) {
        //데이터 검증 및 비밀번호 암호화
        if (memberService.existsByUserName(name)) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
        if (memberService.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);

        MemberDto.MemberRequestDto memberRequestDto =  MemberDto.MemberRequestDto.builder()
                .userName(name)
                .email(email)
                .password(encodedPassword)
                .gender(gender)
                .birth(birth)
                .point(0)
                .build();
        memberRequestDto.setPassword(encodedPassword);

        //회원가입 처리
        memberService.createMember(memberRequestDto);

        return "redirect:/login"; //성공 후 홈 화면 리다이렉트
    }


}