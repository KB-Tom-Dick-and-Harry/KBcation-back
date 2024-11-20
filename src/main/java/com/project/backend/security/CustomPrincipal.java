package com.project.backend.security;

import org.springframework.security.core.userdetails.UserDetails;

public class CustomPrincipal {
    private final Long memberId;
    private final UserDetails userDetails;

    public CustomPrincipal(Long memberId, UserDetails userDetails) {
        this.memberId = memberId;
        this.userDetails = userDetails;
    }

    public Long getMemberId() {
        return memberId;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }
}
