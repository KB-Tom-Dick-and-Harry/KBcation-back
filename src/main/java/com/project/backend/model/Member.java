package com.project.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 50)
    private String userName;

    @Column(length = 20)
    private String gender;

    @Column(length = 20)
    private String birth;

    @Column
    private Integer point;

    @Builder
    public Member(String userName, String gender, String birth, Integer point) {
        this.userName = userName;
        this.gender = gender;
        this.birth = birth;
        this.point = 0;  // 초기 포인트는 0으로 설정
    }

    // 포인트 업데이트 메서드
    public void updatePoint(Integer point) {
        this.point = point;
    }
}
