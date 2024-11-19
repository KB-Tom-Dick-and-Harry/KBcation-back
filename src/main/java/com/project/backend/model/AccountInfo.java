package com.project.backend.model;

import com.project.backend.model.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(length = 1000)
    private String connectedId;

    @Column(nullable = false, length = 50)
    private String organization; // 기관 코드

    @Column(nullable = false, length = 50)
    private String account; // 계좌 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    @Builder
    public AccountInfo(String connectedId, String organization, String account, Member member) {
        this.connectedId = connectedId;
        this.organization = organization;
        this.account = account;
        this.member = member;
    }

    // ConnectedId 업데이트 메서드
    public void updateConnectedId(String connectedId) {
        this.connectedId = connectedId;
    }
}
