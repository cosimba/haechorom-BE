package com.cosimba.dive.domain.user.config;

import com.cosimba.dive.domain.user.security.JwtTokenFilter;
import com.cosimba.dive.domain.user.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // SecurityFilterChain을 설정하여 기존 WebSecurityConfigurerAdapter 대체
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()  // 회원가입, 로그인은 누구나 접근 가능
                        .anyRequest().authenticated()  // 나머지 요청은 인증 필요
                )
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);  // JWT 필터 추가

        return http.build();
    }

    // AuthenticationManager 빈을 제공
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // PasswordEncoder 빈
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
