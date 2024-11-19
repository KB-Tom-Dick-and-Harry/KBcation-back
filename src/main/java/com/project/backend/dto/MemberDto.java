package com.project.backend.dto;

import com.project.backend.entity.MemberEntity;
import com.project.backend.model.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

public  class MemberDto {

    // Request DTO
    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public class MemberDTO {
        private Long id;
        private String memberEmail;
        private String memberPassword;
        private String memberName;
    }

    @Getter
    @Setter
    @NoArgsConstructor

    public static class MemberRequestDto {

        @NotBlank(message = "이름은 필수 입력값입니다.")
        private String userName;


        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        private String password;

        @NotBlank(message = "이메일은 필수 입력값입니다.")
        @Email(message = "유효한 이메일 형식이 아닙니다.")
        private String email;


        @NotBlank(message = "성별은 필수 입력값입니다.")
        private String gender;

        @NotBlank(message = "생년월일은 필수 입력값입니다.")
        private String birth;

        private Integer point;

        @Builder
        public MemberRequestDto(String userName, Long memberId, String email, String password, String gender, String birth, Integer point) {
            this.userName = userName;
            this.email = email; // 이메일 추가
            this.password = password;
            this.gender = gender;
            this.birth = birth;
            this.point = point;
        }

        public MemberEntity toEntity() {
            return MemberEntity.builder()
                    .userName(this.userName)
                    .email(this.email) // 이메일 추가
                    .password(this.password) // 비밀번호 암호화 처리됨
                    .gender(this.gender)
                    .birth(this.birth)
                    .point(this.point == null ? 0 : this.point)
                    .build();
        }
    }

    // Response DTO

    @Getter
    public static class MemberResponseDto {
        private Long memberId;
        private String userName;
        private String email; // 이메일 필드 추가
        private String gender;
        private String birth;
        private Integer point;



        public MemberResponseDto(MemberEntity entity) {
            this.memberId = entity.getMemberId();
            this.userName = entity.getUserName();
            this.email = entity.getEmail();
            this.gender = entity.getGender();
            this.birth = entity.getBirth();
            this.point = entity.getPoint();

        }

        // 정적 팩토리 메서드 추가
        public static MemberResponseDto fromEntity(MemberEntity entity) {
            return new MemberResponseDto(entity);
        }
    }
}
