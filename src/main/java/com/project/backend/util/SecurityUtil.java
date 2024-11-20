package com.project.backend.util;

import com.project.backend.security.CustomPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static Long getCurrentMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomPrincipal) {
            CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
            return principal.getMemberId();
        }
        throw new RuntimeException("로그인된 사용자 정보를 찾을 수 없습니다.");
    }
}
