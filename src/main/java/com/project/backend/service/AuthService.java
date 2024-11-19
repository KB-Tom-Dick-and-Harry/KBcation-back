package com.project.backend.service;

import com.project.backend.dto.LoginRequestDto;
import com.project.backend.dto.LoginResponseDto;
import com.project.backend.dto.MemberDto;
import com.project.backend.dto.RegisterRequestDto;

public interface AuthService {
    MemberDto register(RegisterRequestDto request);
    LoginResponseDto login(LoginRequestDto request);
    void logout(String token);
    boolean isTokenBlacklisted(String token);
}