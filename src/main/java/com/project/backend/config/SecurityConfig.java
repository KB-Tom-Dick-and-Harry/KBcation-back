package com.project.backend.config;


import com.project.backend.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    // PasswordEncoder 빈 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager 빈 등록 (Spring Boot 2.7+ 필요)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    // SecurityFilterChain 설정 (Spring Security 인증/인가 구성)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                    .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/**").permitAll());


//  로그인 기능 필요할 경우 활성화
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/login", "/member/save", "/register", "/public/**").permitAll()
//                        .anyRequest().authenticated()) // 나머지 요청은 인증 필요
//                .formLogin(form -> form
//                        .loginPage("/login") //커스텀 로그인 페이지 경로
//                        .defaultSuccessUrl("/home", true) //로그인 성공 후 이동
//                        .failureUrl("/login?error=true") //로그인 실패 시 이동
//                        .permitAll())
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login")
//                        .permitAll());
        return http.build();
    }
}
