package com.project.backend.repository;

import com.project.backend.model.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption, Integer> {

    //특정 회원의 소비 내역 조회
    List<Consumption> findByMemberId(Long memberId);

    //특정 카테고리의 소비 내역 조회
    List<Consumption> findByCategory(String category);

    // 특정 회원의 소비 내역 중 특정 조건에 해당하는 데이터 존재 여부 확인
    boolean existsByMemberIdAndDateAndSpendingAmountAndConsumptionDetails(
            Long memberId, Date date, Integer spendingAmount, String consumptionDetails);

    // 특정 회원의 최신 소비 내역 3건 조회
    List<Consumption> findTop3ByMemberIdOrderByDateDesc(Long memberId);

    // 특정 회원의 최신 currentBalance 조회
    Optional<Consumption> findTopByMemberIdOrderByDateDesc(Long memberId);

    // 특정 회원의 특정 기간 내 소비 내역 조회
    List<Consumption> findByMemberIdAndDateBetween(Long memberId, Date startDate, Date endDate);

}
