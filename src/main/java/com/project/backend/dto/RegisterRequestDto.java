package com.project.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "회원가입 요청 DTO")
public class RegisterRequestDto {
    @Schema(description = "사용자 아이디", example = "user123")
    @NotBlank(message = "Username is mandatory")
    private String userName;

    @Schema(description = "비밀번호", example = "password123")
    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "Password must be minimum 8 characters, at least one letter and one number")
    private String password;

    @Schema(description = "사용자 이름", example = "홍길동")
    private String fullName;

    @Schema(description = "성별", example = "male")
    private String gender;

    @Schema(description = "생년월일", example = "1990-01-01")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Birth date must be in format YYYY-MM-DD")
    private String birth;

    @Schema(description = "연동 ID", example = "kakao_123456")
    private String connectedId;
}