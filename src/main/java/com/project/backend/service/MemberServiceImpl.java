package com.project.backend.service;

import com.project.backend.dto.MemberDto;
import com.project.backend.model.Member;
import com.project.backend.repository.MemberRepository;
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
        // 사용자 이름 중복 확인
        if (memberRepository.existsByUserName(requestDto.getUserName())) {
            throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다.");
        }

        // 연결된 ID 중복 확인
        if (requestDto.getConnectedId() != null &&
                memberRepository.existsByConnectedId(requestDto.getConnectedId())) {
            throw new IllegalArgumentException("이미 존재하는 연결된 ID입니다.");
        }

        // DTO -> Entity 변환 후 저장
        Member member = memberRepository.save(requestDto.toEntity());
        return member.getMemberId();
    }

    @Override
    public List<MemberDto.MemberResponseDto> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(MemberDto.MemberResponseDto::fromEntity) // Entity -> DTO 변환
                .collect(Collectors.toList());
    }

    @Override
    public MemberDto.MemberResponseDto getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        return MemberDto.MemberResponseDto.fromEntity(member);
    }

    @Override
    public MemberDto.MemberResponseDto getMemberByUserName(String userName) {
        Member member = memberRepository.findByUserName(userName)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 이름입니다."));
        return MemberDto.MemberResponseDto.fromEntity(member);
    }

    @Override
    public MemberDto.MemberResponseDto getMemberByConnectedId(String connectedId) {
        Member member = memberRepository.findByConnectedId(connectedId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 연결된 ID입니다."));
        return MemberDto.MemberResponseDto.fromEntity(member);
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

    @Override
    public boolean existsByUserName(String userName) {
        return memberRepository.existsByUserName(userName);
    }

    @Override
    public boolean existsByConnectedId(String connectedId) {
        return memberRepository.existsByConnectedId(connectedId);
    }

    @Override
    public MemberDto.MemberResponseDto getMemberByUserNameAndConnectedIdIsNull(String userName) {
        Member member = memberRepository.findByUserNameAndConnectedIdIsNull(userName)
                .orElseThrow(() -> new IllegalArgumentException("해당 조건을 만족하는 사용자가 없습니다."));
        return MemberDto.MemberResponseDto.fromEntity(member);
    }
}
