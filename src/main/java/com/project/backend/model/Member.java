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

    @Column(length = 1000)
    private String connectedId;

    @Builder
    public Member(String userName, String gender, String birth, Integer point, String connectedId) {
        this.userName = userName;
        this.gender = gender;
        this.birth = birth;
        this.point = 0;  // 초기 포인트는 0으로 설정
        this.connectedId = connectedId;
    }

    // 포인트 업데이트 메서드
    public void updatePoint(Integer point) {
        this.point = point;
    }

    // ConnectedId 업데이트 메서드
    public void updateConnectedId(String connectedId) {
        this.connectedId = connectedId;
    }

}
