package com.project.backend.dto;

import com.project.backend.model.AccountInfo;
import com.project.backend.model.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class AccountInfoDto {

    @Getter
    @NoArgsConstructor
    public static class AccountInfoRequestDto {
        private String organization; // 기관 코드
        private String account; // 계좌 번호
        private String id; // 사용자 금융 ID
        private String password; // 사용자 금융 비밀번호

        @Builder
        public AccountInfoRequestDto(String organization, String account, String id, String password) {
            this.organization = organization;
            this.account = account;
            this.id = id;
            this.password = password;
        }
    }

    // Response DTO
    @Getter
    public static class AccountInfoResponseDto {
        private Long accountId;
        private String connectedId;
        private String organization;
        private String account;

        public AccountInfoResponseDto(AccountInfo accountInfo) {
            this.accountId = accountInfo.getAccountId();
            this.connectedId = accountInfo.getConnectedId();
            this.organization = accountInfo.getOrganization();
            this.account = accountInfo.getAccount();
        }
    }
}
