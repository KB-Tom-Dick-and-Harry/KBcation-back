package com.project.backend.repository;

import com.project.backend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // userName으로 회원 찾기
    Optional<Member> findByUserName(String userName);

    // userName 존재 여부 확인
    boolean existsByUserName(String userName);
}