package com.project.backend.repository;

import com.project.backend.model.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption, Integer> {

    //특정 회원의 소비 내역 조회
    List<Consumption> findByMemberId(Integer memberId);

    //특정 카테고리의 소비 내역 조회
    List<Consumption> findByCategory(String category);
}
