package com.project.backend.service;

import com.project.backend.dto.*;
import com.project.backend.model.Member;
import com.project.backend.repository.MemberRepository;
import com.project.backend.security.JwtAuthenticationFilter;
import com.project.backend.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;  // Changed from JwtAuthenticationFilter
    private final Set<String> blacklistedTokens = new HashSet<>();

    @Override
    @Transactional
    public MemberDto register(RegisterRequestDto request) {
        if (memberRepository.findByUserName(request.getUserName()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Member member = Member.builder()
                .userName(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .gender(request.getGender())
                .birth(request.getBirth())
                .point(0)
                .connectedId(request.getConnectedId())
                .build();

        return convertToDTO(memberRepository.save(member));
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto request) {
        Member member = memberRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtTokenProvider.generateToken(member.getUserName());

        return LoginResponseDto.builder()
                .memberId(member.getMemberId())
                .token(token)
                .type("Bearer")
                .userName(member.getUserName())
                .fullName(member.getFullName())
                .gender(member.getGender())
                .birth(member.getBirth())
                .point(member.getPoint())
                .connectedId(member.getConnectedId())
                .build();
    }

    @Override
    public void logout(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7);
            jwtTokenProvider.blacklistToken(jwt);
        }
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        return jwtTokenProvider.isTokenBlacklisted(token);
    }

    private MemberDto convertToDTO(Member member) {
        return MemberDto.builder()
                .memberId(member.getMemberId())
                .userName(member.getUserName())
                .fullName(member.getFullName())
                .gender(member.getGender())
                .birth(member.getBirth())
                .point(member.getPoint())
                .connectedId(member.getConnectedId())
                .build();
    }
}
