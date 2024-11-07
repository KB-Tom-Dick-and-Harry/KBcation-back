package com.project.backend.dto;

import com.project.backend.model.Consumption;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;


public class ConsumptionDto {

    //Request DTO
    @Getter
    @NoArgsConstructor
    public static class ConsumptionRequestDto {
        private Integer memberId;

        @NotBlank(message = "소비 내역은 필수 입력값입니다.")
        private String consumptionDetails;

        @NotBlank(message = "카테고리는 필수 입력값입니다. ")
        private String category;

        private Integer spendingAmount;
        private Date date;

        @Builder
        public ConsumptionRequestDto(Integer memberId, String consumptionDetails, String category, Integer spendingAmount, String date) {
            this.memberId = memberId;
            this.consumptionDetails = consumptionDetails;
            this.category = category;
            this.spendingAmount = spendingAmount;
            this.date = date;
        }


        public Consumption toEntity() {
            return Consumption.builder()
                    .memberId(memberId)
                    .consumptionDetails(consumptionDetails)
                    .category(category)
                    .spendingAmount(spendingAmount)
                    .date(date)
                    .build();
        }

    }


    // Response DTO
    @Getter
    public static class ConsumptionResponseDto {
        private Integer consumptionId;
        private Integer memberId;
        private String consumptionDetails;
        private String category;
        private Integer spendingAmount;
        private Date date;

    public ConsumptionResponseDto(Consumption consumption) {
            this.consumptionId = consumption.getConsumptionId();
            this.memberId = consumption.getMemberId();
            this.consumptionDetails = consumption.getConsumptionDetails();
            this.category = consumption.getCategory();
            this.spendingAmount = consumption.getSpendingAmount();
            this.date = consumption.getDate();
    }

    }
}
