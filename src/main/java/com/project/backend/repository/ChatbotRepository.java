package com.project.backend.repository;

import com.project.backend.model.Chatbot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatbotRepository extends JpaRepository<Chatbot, Long> {

    // 특정 memberId를 가진 Chatbot 엔티티 목록 조회
    List<Chatbot> findByMemberId(Long memberId);
}
