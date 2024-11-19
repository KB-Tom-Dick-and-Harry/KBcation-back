package com.project.backend.service;

import com.project.backend.dto.MemberDto;
import com.project.backend.entity.MemberEntity;
import com.project.backend.model.Member;
import com.project.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final CodefService codefService;
    private final PasswordEncoder passwordEncoder;

    //1.회원가입
    @Override
    @Transactional
    public Long createMember(MemberDto.MemberRequestDto requestDto) {
        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        if (memberRepository.existsByUserName(requestDto.getUserName())) {
            throw new IllegalArgumentException("이미 존재하는 회원 이름입니다.");
        }


        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        requestDto.setPassword(encodedPassword);

        // 엔티티 생성 및 저장
        Member memberEntity = requestDto.toEntity(); // 수정
        Member savedMember = memberRepository.save(memberEntity); // 저장된 엔티티 반환
        return savedMember.getMemberId(); // ID 반환
    }


    //2.로그인
    public MemberDto.MemberResponseDto login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return new MemberDto.MemberResponseDto(member);
    }

    //3.회원 전체 조회
    @Override
    public List<MemberDto.MemberResponseDto> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(MemberDto.MemberResponseDto::fromEntity) // 정적 팩토리 메서드 사용
                .collect(Collectors.toList());
    }

    //4.특정 회원 조회
    @Override
    public MemberDto.MemberResponseDto getMember(Long memberId) {
        Member memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        return MemberDto.MemberResponseDto.fromEntity(memberEntity); // 정적 팩토리 메서드 사용
    }

    // 5.회원 포인트 업데이트
    @Override
    @Transactional
    public void updateMemberPoint(Long memberId, Integer point) {
        //MEMBER를 안전하게 가져옵니다.
        Member memberEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 회원입니다."));

        // 포인트 업데이트
        memberEntity.setPoint(point);

        //  변경 사항 자동 저장(JPA의 변경 감지 기능 활용)
    }

    // 6.회원 삭제
    @Override
    @Transactional
    public void deleteMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        memberRepository.deleteById(memberId);
    }

    //7.사용자 이름 중복 확인
    @Override
    public boolean existsByUserName(String name) {
        // repository를 사용하여 데이터베이스에서 사용자 이름이 존재하는지 확인
        return memberRepository.existsByUserName(name);

    }
    @Override
    public boolean existsByEmail(String email) {
        // repository를 사용하여 데이터베이스에서 이메일이 존재하는지 확인
        return memberRepository.existsByEmail(email);
    }
}