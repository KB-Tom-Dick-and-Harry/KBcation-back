package com.project.backend.model;

import com.project.backend.dto.ConsumptionDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

public class Consumption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer consumptionId;

    private Integer memberId;
    private String consumptionDetails;
    private String category;
    private  Integer spendingAmount;
    private Date date;

    // 업데이트 메서드
    public void updateDetails(ConsumptionDto.ConsumptionRequestDto requestDto){
        this.consumptionDetails = requestDto.getConsumptionDetails();
        this.category = requestDto.getCategory();
        this.spendingAmount = requestDto.getSpendingAmount();
        this.date = requestDto.getDate();
    }
}
