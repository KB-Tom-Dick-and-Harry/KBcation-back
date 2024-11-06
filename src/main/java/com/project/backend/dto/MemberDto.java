package com.project.backend.dto;

import com.project.backend.model.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberDto {

    // Request DTO
    @Getter
    @NoArgsConstructor
    public static class MemberRequestDto {
        @NotBlank(message = "이름은 필수 입력값입니다.")
        private String userName;

        @NotBlank(message = "성별은 필수 입력값입니다.")
        private String gender;

        @NotBlank(message = "생년월일은 필수 입력값입니다.")
        private String birth;

        private Integer point;

        @Builder
        public MemberRequestDto(String userName, String gender, String birth, Integer point) {
            this.userName = userName;
            this.gender = gender;
            this.birth = birth;
            this.point = point;
        }

        public Member toEntity() {
            return Member.builder()
                    .userName(userName)
                    .gender(gender)
                    .birth(birth)
                    .point(point)
                    .build();
        }
    }

    // Response DTO
    @Getter
    public static class MemberResponseDto {
        private Long memberId;
        private String userName;
        private String gender;
        private String birth;
        private Integer point;

        public MemberResponseDto(Member member) {
            this.memberId = member.getMemberId();
            this.userName = member.getUserName();
            this.gender = member.getGender();
            this.birth = member.getBirth();
            this.point = member.getPoint();
        }
    }
}
