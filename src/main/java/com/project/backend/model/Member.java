package com.project.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) //JPA 요구사항 : 기본 생성자 필요
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 50,nullable = false)
    private String userName;

    @Column(length = 60, nullable = false) // 암호화된 비밀번호는 일반적으로 최대 60자
    private String password; // 비밀번호 필드 추가

    @Column(length = 20)
    private String gender;

    @Column(length = 20)
    private String birth;

    @Column(nullable = false)
    private Integer point = 0; //기본값 설정

    @Column(length = 50)
    private String connectedId;

    //포인트 업데이트 메서드
    public void updatePoint(Integer point) {
        this.point = point;
    }

    @Builder
    public Member(String userName, String gender, String birth, Integer point, String connectedId) {
        this.userName = userName;
        this.password = password;// Builder에서 비밀번호 설정
        this.gender = gender;
        this.birth = birth;
        this.point = point == null ? 0 : point;  // 초기 포인트는 0으로 설정
        this.connectedId = connectedId;
    }

    // 포인트 업데이트 메서드
    public void updatePoint(Integer point) {
        this.point = point;
    }

}
