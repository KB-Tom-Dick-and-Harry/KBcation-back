package com.project.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.codef.api.EasyCodef;
import io.codef.api.EasyCodefServiceType;
import io.codef.api.EasyCodefUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CodefService {

    private final EasyCodef codef;

    // `application-secret.properties`에서 값을 주입받음
    @Value("${codef.demo.client-id}")
    private String demoClientId;

    @Value("${codef.demo.client-secret}")
    private String demoClientSecret;

    @Value("${codef.public-key}")
    private String publicKey;

    public CodefService() {
        this.codef = new EasyCodef();
    }

    // Codef 설정 초기화 메서드
    @PostConstruct
    public void initializeCodef() {
        codef.setClientInfoForDemo(demoClientId, demoClientSecret); // Demo Client 정보 설정
        codef.setPublicKey(publicKey); // RSA Public Key 설정
    }

    public String getAccessToken() {
        // 토큰 자동 관리: 라이브러리가 자동으로 처리
        try {
            return codef.requestToken(EasyCodefServiceType.DEMO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * CODEF 계정 등록 및 Connected ID 발급
     * @param accountInfo 고객의 계좌 정보 (JSON 형식으로 제공)
     * @return Connected ID
     */
    public String registerAccount(Map<String, String> accountInfo) {
        List<HashMap<String, Object>> accountList = new ArrayList<>();
        HashMap<String, Object> accountMap = new HashMap<>();

        // 요청 파라미터 설정
        accountMap.put("countryCode", "KR"); // 국가코드
        accountMap.put("businessType", "BK"); // 업무구분(BK : 은행, 저축은행)
        accountMap.put("clientType", "P"); // 고객구분(P: 개인)
        accountMap.put("organization", accountInfo.get("organization")); // 기관코드(은행코드)
        accountMap.put("loginType", "1"); // 로그인 방식 (1: 아이디/패스워드)
        accountMap.put("id", accountInfo.get("id")); // 사용자의 금융 계좌 아이디
        accountMap.put("birthday", accountInfo.get("birthday")); //생년월일
        try {
            accountMap.put("password",  EasyCodefUtil.encryptRSA(accountInfo.get("password"), codef.getPublicKey())); // RSA암호화가 필요한 필드는 encryptRSA(String plainText, String publicKey) 메서드를 이용해 암호화
        } catch (Exception e) {
            throw new RuntimeException("RSA 암호화 실패", e);
        }

        accountList.add(accountMap);

        HashMap<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("accountList", accountList);

        // Connected ID 발급 요청
        try {
            String responseJson = codef.createAccount(EasyCodefServiceType.DEMO, parameterMap);

            // JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseJson);
            JsonNode dataNode = rootNode.get("data");
            if (dataNode != null && dataNode.has("connectedId")) {
                return dataNode.get("connectedId").asText(); // connectedId 추출
            } else {
                throw new RuntimeException("connectedId를 찾을 수 없습니다.");
            }
        } catch (UnsupportedEncodingException | JsonProcessingException | InterruptedException e) {
            throw new RuntimeException("Connected ID 발급 요청 실패", e);
        }
    }

    /**
     * 거래내역 조회 요청
     *
     * @param connectedId 사용자 Connected ID
     * @param accountInfo 사용자 계좌 정보 (아이디, 비밀번호, 계좌번호 등)
     * @return 거래내역 API 응답 결과
     */

    public String getTransactionList(String connectedId, Map<String, String> accountInfo, String startDate, String endDate) {
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("organization", accountInfo.get("organization"));
        requestBody.put("connectedId", connectedId);
        requestBody.put("account", accountInfo.get("account"));
        requestBody.put("startDate", startDate);
        requestBody.put("endDate", endDate);
        requestBody.put("orderBy", "0"); //최신순 정렬
        requestBody.put("inquiryType", "1"); //기본 조회

        // CODEF 거래내역 조회 API 호출
        try {
            return codef.requestProduct("/v1/kr/bank/p/account/transaction-list", EasyCodefServiceType.DEMO, requestBody);
        } catch (Exception e) {
            throw new RuntimeException("거래내역 조회 실패", e);
        }
    }
}
