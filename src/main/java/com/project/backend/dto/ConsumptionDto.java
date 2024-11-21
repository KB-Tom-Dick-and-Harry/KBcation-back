package com.project.backend.dto;

import com.project.backend.model.Consumption;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ConsumptionDto {

    //Request DTO
    @Getter
    @NoArgsConstructor
    public static class ConsumptionRequestDto {
        private Long memberId;

        @NotBlank(message = "소비 내역은 필수 입력값입니다.")
        private String consumptionDetails;

        @NotBlank(message = "카테고리는 필수 입력값입니다. ")
        private String category;

        private Integer spendingAmount;
        private Date date;

        private Integer currentBalance;

        @Builder
        public ConsumptionRequestDto(Long memberId, String consumptionDetails, String category, Integer spendingAmount, Date date, Integer currentBalance) {
            this.memberId = memberId;
            this.consumptionDetails = consumptionDetails;
            this.category = category;
            this.spendingAmount = spendingAmount;
            this.date = date;
            this.currentBalance = currentBalance;
        }


        public Consumption toEntity() {
            return Consumption.builder()
                    .memberId(memberId)
                    .consumptionDetails(consumptionDetails)
                    .category(category)
                    .spendingAmount(spendingAmount)
                    .date(date)
                    .currentBalance(currentBalance)
                    .build();
        }

    }


    // Response DTO
    @Getter
    public static class ConsumptionResponseDto {
        private Integer consumptionId;
        private Long memberId;
        private String consumptionDetails;
        private String category;
        private Integer spendingAmount;
        private Date date;
        private Integer currentBalance;

        public ConsumptionResponseDto(Consumption consumption) {
            this.consumptionId = consumption.getConsumptionId();
            this.memberId = consumption.getMemberId();
            this.consumptionDetails = consumption.getConsumptionDetails();
            this.category = consumption.getCategory();
            this.spendingAmount = consumption.getSpendingAmount();
            this.date = consumption.getDate();
            this.currentBalance = consumption.getCurrentBalance();
    }

    }
}
