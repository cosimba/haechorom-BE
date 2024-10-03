package com.cosimba.dive.domain.user.controller;

import com.cosimba.dive.domain.user.entity.UserEntity;
import com.cosimba.dive.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/auth")
public class AuthController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserEntity userEntity) {
        try {
            UserEntity savedUser = userService.register(userEntity);
            return ResponseEntity.ok(savedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("중복된 아이디입니다.");
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String userId = loginRequest.get("userId");
        String password = loginRequest.get("password");

        String token = userService.login(userId, password);
//        return ResponseEntity.ok("Bearer " + token);

        // 헤더에 토큰 추가
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token) // 토큰을 Authorization 헤더에 담아 반환
                .body("로그인 성공");
    }

    // 아이디 중복 확인
    @GetMapping("/check/{userId}")
    public ResponseEntity<Boolean> checkUserIdDuplicate(@PathVariable("userId") String userId) {
        boolean isDuplicate = userService.isUserIdDuplicate(userId);
        return ResponseEntity.ok(isDuplicate);
    }

    // 아이디/비밀번호 찾기는 나중에 구현

}
