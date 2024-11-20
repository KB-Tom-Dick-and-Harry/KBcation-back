package com.project.backend.dto;

import com.project.backend.model.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "회원 정보 DTO")
public class MemberDto {

    @Schema(description = "회원 ID", example = "1")
    private Long memberId;

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

    public static MemberDto fromEntity(Member member) {
        return MemberDto.builder()
                .memberId(member.getMemberId())
                .userName(member.getUserName())
                .fullName(member.getFullName())
                .gender(member.getGender())
                .birth(member.getBirth())
                .point(member.getPoint())
                .connectedId(member.getConnectedId())
                .build();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "회원 생성 요청 DTO")
    public static class MemberRequestDto {

        private String userName;
        private String password;
        private String fullName;
        private String gender;
        private String birth;
        private Integer point;
        private String connectedId;

        public Member toEntity() {
            return Member.builder()
                    .userName(this.userName)
                    .password(this.password)
                    .fullName(this.fullName)
                    .gender(this.gender)
                    .birth(this.birth)
                    .point(this.point != null ? this.point : 0)
                    .connectedId(this.connectedId)
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "회원 응답 DTO")
    public static class MemberResponseDto {

        private Long memberId;
        private String userName;
        private String fullName;
        private String gender;
        private String birth;
        private Integer point;
        private String connectedId;

        public static MemberResponseDto fromEntity(Member member) {
            return MemberResponseDto.builder()
                    .memberId(member.getMemberId())
                    .userName(member.getUserName())
                    .fullName(member.getFullName())
                    .gender(member.getGender())
                    .birth(member.getBirth())
                    .point(member.getPoint())
                    .connectedId(member.getConnectedId())
                    .build();
        }
    }
}
