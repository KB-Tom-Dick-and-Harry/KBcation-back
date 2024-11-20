package com.project.backend.service;

import com.project.backend.dto.MemberDto;
import com.project.backend.model.Member;
import com.project.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public Long createMember(MemberDto.MemberRequestDto requestDto) {
        if (memberRepository.existsByUserName(requestDto.getUserName())) {
            throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다.");
        }

        if (requestDto.getConnectedId() != null &&
                memberRepository.existsByConnectedId(requestDto.getConnectedId())) {
            throw new IllegalArgumentException("이미 존재하는 연결된 ID입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        requestDto.setPassword(encodedPassword);

        Member member = memberRepository.save(requestDto.toEntity());
        return member.getMemberId();
    }

    @Override
    public List<MemberDto.MemberResponseDto> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(MemberDto.MemberResponseDto::fromEntity)
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

    @Override
    public MemberDto.MemberResponseDto getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        Member member = memberRepository.findByUserName(currentUserName)
                .orElseThrow(() -> new IllegalStateException("현재 인증된 사용자를 찾을 수 없습니다."));

        return MemberDto.MemberResponseDto.fromEntity(member);
    }

    @Override
    @Transactional
    public MemberDto.MemberResponseDto updateCurrentMember(MemberDto.MemberRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        Member member = memberRepository.findByUserName(currentUserName)
                .orElseThrow(() -> new IllegalStateException("현재 인증된 사용자를 찾을 수 없습니다."));

        member.setFullName(requestDto.getFullName());
        member.setGender(requestDto.getGender());
        member.setBirth(requestDto.getBirth());

        if (requestDto.getPassword() != null && !requestDto.getPassword().isEmpty()) {
            member.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        }

        Member updatedMember = memberRepository.save(member);

        return MemberDto.MemberResponseDto.fromEntity(updatedMember);
    }
}