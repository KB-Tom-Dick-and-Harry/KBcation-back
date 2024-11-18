package com.project.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.codef.api.EasyCodef;
import io.codef.api.EasyCodefServiceType;
import io.codef.api.EasyCodefUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class CodefService {

    private final EasyCodef codef;

    public CodefService() {
        // EasyCodef 객체 생성
        this.codef = new EasyCodef();

        // 데모 클라이언트 정보 설정
        codef.setClientInfoForDemo("YOUR_DEMO_CLIENT_ID", "YOUR_DEMO_CLIENT_SECRET");

        // RSA 암호화를 위한 퍼블릭 키 설정
        codef.setPublicKey("YOUR_PUBLIC_KEY");
    }

    public String getAccessToken() {
        // 토큰 자동 관리: 라이브러리가 자동으로 처리
        try {
            return codef.requestToken(EasyCodefServiceType.SANDBOX);
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
            return codef.createAccount(EasyCodefServiceType.DEMO, parameterMap);
        } catch (UnsupportedEncodingException | JsonProcessingException | InterruptedException e) {
            throw new RuntimeException("Connected ID 발급 요청 실패", e);
        }
    }
}
