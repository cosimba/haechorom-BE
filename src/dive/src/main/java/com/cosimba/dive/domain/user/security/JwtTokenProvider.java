package com.cosimba.dive.domain.user.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private final String SECRET_KEY = "yourSecretKey";  // 비밀 키

    // JWT 토큰 생성
    public String createToken(String userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10시간 유효기간
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // 비밀번호 재설정 토큰 생성 (유효기간 짧게 설정)
    public String createPasswordResetToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "RESET_PASSWORD");  // 비밀번호 재설정 요청을 위한 토큰임을 명시

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))  // 15분 유효
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // JWT 토큰에서 사용자 ID 추출
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();  // 사용자 ID가 subject에 포함됩니다.
    }

    // 토큰에서 인증 정보 추출
    public Authentication getAuthentication(String token) {
        String userId = getUserIdFromToken(token);
        // 사용자 정보를 가져오는 로직 추가 (예: UserDetailsService)
        UserDetails userDetails = loadUserByUserId(userId);  // loadUserByUserId는 UserDetailsService에서 구현된 메서드여야 함.
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // UserDetailsService에서 사용자 정보를 로드하는 메서드 (구현 필요)
    public UserDetails loadUserByUserId(String userId) {
        // UserDetailsService에서 사용자 정보를 로드하는 로직을 추가하세요.
        return null;  // 실제 UserDetailsService 구현체가 필요합니다.
    }
}
