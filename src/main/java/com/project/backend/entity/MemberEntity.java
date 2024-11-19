package com.project.backend.entity;

import com.project.backend.dto.MemberDto;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Setter
@Getter
@Table(name = "member_table")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long memberId;

    @Column(length = 50, unique = true, nullable = false) // 유니크 속성 및 필수 값 설정
    private String userName;

    @Column(length = 50, unique = true, nullable = false) // 이메일 유니크 및 필수 값 설정
    private String email;

    @Column(length = 50, nullable = false) // 암호화된 비밀번호 길이 고려
    private String password;

    @Column(length = 10)
    private String gender;

    @Column(length = 20)
    private String birth;

    @Builder.Default
    @Column(nullable = false)
    private Integer point = 0;


    // DTO -> MemberEntity 변환 메서드
    public static MemberEntity toEntity(MemberDto.MemberRequestDto requestDto) {
        return MemberEntity.builder()
                .userName(requestDto.getUserName())
                .email(requestDto.getEmail()) // 이메일 추가
                .password(requestDto.getPassword()) // 암호화된 비밀번호 전달
                .gender(requestDto.getGender())
                .birth(requestDto.getBirth())
                .point(requestDto.getPoint() == null ? 0 : requestDto.getPoint())
                .build();
    }



}


