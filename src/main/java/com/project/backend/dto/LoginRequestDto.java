package com.project.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "로그인 요청 DTO")
public class LoginRequestDto {
    @Schema(description = "사용자 아이디", example = "user123")
    @NotBlank(message = "userName is mandatory")
    private String userName;

    @Schema(description = "사용자 비밀번호", example = "password123")
    @NotBlank(message = "Password is mandatory")
    private String password;
}
