package com.project.backend.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    //기본페이지 요청 메서드
    @GetMapping("/")
    public String index() {
        return "index"; //=> templates 폴더의 index.html을 찾아감
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";// templates/login.html 반환
    }
    // 회원가입 폼 페이지 요청 (GET)
    @GetMapping("/member/save")
    public String saveForm() {
        return "save"; // templates/save.html 반환
    }

    @PostMapping("/member/save")
    public String saveMember(
            @RequestParam("memberEmail") String email,
            @RequestParam("memebrPassword") String password,
            @RequestParam("memberName") String name
    ) {
       //데이터 처리 (예: 데이터베이스 저장)
        System.out.println("회원 정보:" + email + ", " + name);

        //성공적으로 처리 후 홈 화면으로 리다이렉트
        return "redirect:/";

    }

}
