package com.cosimba.dive.domain.user.controller;

import com.cosimba.dive.domain.user.entity.UserEntity;
import com.cosimba.dive.domain.user.security.JwtTokenProvider;
import com.cosimba.dive.domain.user.service.MailService;
import com.cosimba.dive.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final MailService mailService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, MailService mailService,
                          JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.mailService = mailService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<?> join(@RequestBody UserEntity userEntity) {
        UserEntity savedUser = userService.register(userEntity);
        return ResponseEntity.ok(savedUser);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String userId, @RequestParam String password) {
        String token = userService.login(userId, password);
        return ResponseEntity.ok(token);
    }

    // 아이디 찾기
    @GetMapping("/find-id")
    public ResponseEntity<?> findUserId(@RequestParam String name, @RequestParam String email) {
        Optional<UserEntity> user = userService.findByEmail(email);
        if (user.isPresent() && user.get().getName().equals(name)) {
            return ResponseEntity.ok(user.get().getUserId());
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    // 비밀번호 재설정 요청 (이메일로 비밀번호 재설정 링크 전송)
    @PostMapping("/reset-password-request")
    public ResponseEntity<?> resetPasswordRequest(@RequestParam String email) {
        Optional<UserEntity> user = userService.findByEmail(email);
        if (user.isPresent()) {
            // 비밀번호 재설정 토큰 생성
            String resetToken = jwtTokenProvider.createPasswordResetToken(user.get().getUserId());

            // 비밀번호 재설정 링크 (이메일로 전송하는 로직을 추가하세요)
            String resetLink = "http://localhost:8080/api/auth/reset-password?token=" + resetToken;

            // 이메일 전송
            mailService.sendPasswordResetEmail(email, resetLink);

            return ResponseEntity.ok("비밀번호 재설정 링크가 " + email + "로 전송되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("Email not found");
        }
    }

    // 비밀번호 재설정 (새로운 비밀번호 설정)
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        // 토큰 검증 및 사용자 ID 추출
        if (jwtTokenProvider.validateToken(token)) {
            String userId = jwtTokenProvider.getUserIdFromToken(token);
            Optional<UserEntity> user = userService.findByUserId(userId);

            if (user.isPresent()) {
                // 새 비밀번호 설정 (암호화 후 저장)
                user.get().setPassword(passwordEncoder.encode(newPassword));
                userService.updateUser(user.get());
                return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
            } else {
                return ResponseEntity.badRequest().body("사용자를 찾을 수 없습니다.");
            }
        } else {
            return ResponseEntity.badRequest().body("유효하지 않은 토큰입니다.");
        }
    }
}
