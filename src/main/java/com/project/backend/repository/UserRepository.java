package com.project.backend.repository;

import com.project.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // username을 기준으로 사용자 정보를 조회하는 메서드
    Optional<UserEntity> findByUsername(String username);
}
