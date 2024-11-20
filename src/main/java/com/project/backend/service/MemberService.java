package com.project.backend.service;

import com.project.backend.dto.MemberDto;

import java.util.List;

public interface MemberService {

    Long createMember(MemberDto.MemberRequestDto requestDto);
    List<MemberDto.MemberResponseDto> getAllMembers();
    MemberDto.MemberResponseDto getMember(Long memberId);
    MemberDto.MemberResponseDto getMemberByUserName(String userName);
    MemberDto.MemberResponseDto getMemberByConnectedId(String connectedId); // 메서드 추가
    void updateMemberPoint(Long memberId, Integer point);
    void deleteMember(Long memberId);
    boolean existsByUserName(String userName);
    boolean existsByConnectedId(String connectedId);
    MemberDto.MemberResponseDto getMemberByUserNameAndConnectedIdIsNull(String userName);
    MemberDto.MemberResponseDto getCurrentMember();
    MemberDto.MemberResponseDto updateCurrentMember(MemberDto.MemberRequestDto requestDto);

}
