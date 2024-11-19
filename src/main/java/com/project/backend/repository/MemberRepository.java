package com.project.backend.repository;

import com.project.backend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserName(String userName);
    boolean existsByUserName(String userName);
    Optional<Member> findByConnectedId(String connectedId);
    boolean existsByConnectedId(String connectedId);

    @Query("SELECT m FROM Member m WHERE m.userName = :userName AND m.connectedId IS NULL")
    Optional<Member> findByUserNameAndConnectedIdIsNull(@Param("userName") String userName);
}