package com.project.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 50, nullable = false)
    private String userName;

    @Column(length = 60, nullable = false)
    private String password;

    @Column(length = 20)
    private String fullName;

    @Column(length = 20)
    private String gender;

    @Column(length = 20)
    private String birth;

    @Column(nullable = false)
    private Integer point = 0;

    @Column(length = 50)
    private String connectedId;

    public void updatePoint(Integer point) {
        this.point = point;
    }

    @Builder
    public Member(String userName, String password, String fullName, String gender, String birth, Integer point, String connectedId) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.gender = gender;
        this.birth = birth;
        this.point = point == null ? 0 : point;
        this.connectedId = connectedId;
    }
}