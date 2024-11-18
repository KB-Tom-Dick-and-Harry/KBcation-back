package com.project.backend.service;

import io.codef.api.EasyCodef;
import io.codef.api.EasyCodefServiceType;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
            return codef.requestToken(EasyCodefServiceType.DEMO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
