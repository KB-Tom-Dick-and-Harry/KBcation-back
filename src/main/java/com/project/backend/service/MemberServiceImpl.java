package com.project.backend.service.impl;

import com.project.backend.dto.MemberDto;
import com.project.backend.model.Member;
import com.project.backend.repository.MemberRepository;
import com.project.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Long createMember(MemberDto.MemberRequestDto requestDto) {
        if (memberRepository.existsByUserName(requestDto.getUserName())) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
        Member member = memberRepository.save(requestDto.toEntity());
        return member.getMemberId();
    }

    @Override
    public List<MemberDto.MemberResponseDto> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(MemberDto.MemberResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public MemberDto.MemberResponseDto getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        return new MemberDto.MemberResponseDto(member);
    }

    @Override
    @Transactional
    public void updateMemberPoint(Long memberId, Integer point) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        member.updatePoint(point);
    }

    @Override
    @Transactional
    public void deleteMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        memberRepository.deleteById(memberId);
    }
}