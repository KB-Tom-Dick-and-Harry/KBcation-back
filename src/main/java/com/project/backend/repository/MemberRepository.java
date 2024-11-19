package com.project.backend.repository;

import com.project.backend.entity.MemberEntity;
import com.project.backend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByUserName(String userName);
    boolean existsByEmail(String email);
    boolean existsByUserName(String userName);
}