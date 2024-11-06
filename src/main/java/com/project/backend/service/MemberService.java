package com.project.backend.service;

import com.project.backend.dto.MemberDto;

import java.util.List;

public interface MemberService {
    // 회원 생성
    Long createMember(MemberDto.MemberRequestDto requestDto);

    // 회원 전체 조회
    List<MemberDto.MemberResponseDto> getAllMembers();

    // 회원 단일 조회
    MemberDto.MemberResponseDto getMember(Long memberId);

    // 회원 포인트 업데이트
    void updateMemberPoint(Long memberId, Integer point);

    // 회원 삭제
    void deleteMember(Long memberId);
}
