package com.project.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "로그인 응답 DTO")
public class LoginResponseDto {
    @Schema(description = "회원 ID", example = "1")
    private Long memberId;

    @Schema(description = "JWT 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Schema(description = "사용자 아이디", example = "user123")
    private String userName;

    @Schema(description = "사용자 이름", example = "홍길동")
    private String fullName;

    @Schema(description = "성별", example = "male")
    private String gender;

    @Schema(description = "생년월일", example = "1990-01-01")
    private String birth;

    @Schema(description = "포인트", example = "100")
    private Integer point;

    @Schema(description = "연동 ID", example = "kakao_123456")
    private String connectedId;
}