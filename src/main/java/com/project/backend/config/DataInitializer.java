package com.project.backend.config;

import com.project.backend.model.Member;
import com.project.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final MemberRepository memberRepository;

    @PostConstruct
    @Transactional
    public void init() {
        // Member가 존재하지 않을 경우에만 생성
        if (memberRepository.findByUserName("홍길동").isEmpty()) {
            Member member = Member.builder()
                    .userName("홍길동")
                    .gender("Male")
                    .birth("1990-01-01")
                    .point(0)
                    .build();
            memberRepository.save(member);
            System.out.println("Member가 생성되었습니다. ID: " + member.getMemberId());
        }
    }
}