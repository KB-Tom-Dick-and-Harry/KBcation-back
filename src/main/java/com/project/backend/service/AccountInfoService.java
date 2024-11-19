package com.project.backend.service;

import com.project.backend.dto.AccountInfoDto;

public interface AccountInfoService {
    AccountInfoDto.AccountInfoResponseDto saveAccountInfo(Long memberId, String connectedId, AccountInfoDto.AccountInfoRequestDto requestDto);
}
