package com.project.backend.service;

import com.project.backend.dto.AccountInfoDto;
import com.project.backend.model.AccountInfo;
import com.project.backend.model.Member;
import com.project.backend.repository.AccountInfoRepository;
import com.project.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountInfoServiceImpl implements AccountInfoService{

    private final MemberRepository memberRepository;
    private final AccountInfoRepository accountInfoRepository;
    private final CodefService codefService;

    @Transactional
    @Override
    public AccountInfoDto.AccountInfoResponseDto saveAccountInfo(Long memberId, String connectedId, AccountInfoDto.AccountInfoRequestDto requestDto) {
        // Member 엔티티 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        // AccountInfo 엔티티 저장
        AccountInfo accountInfo = AccountInfo.builder()
                .connectedId(connectedId)
                .organization(requestDto.getOrganization())
                .account(requestDto.getAccount())
                .member(member)
                .build();

        accountInfoRepository.save(accountInfo);

        return new AccountInfoDto.AccountInfoResponseDto(accountInfo);
    }
}
