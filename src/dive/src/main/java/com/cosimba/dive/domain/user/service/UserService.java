package com.cosimba.dive.domain.user.service;

import com.cosimba.dive.domain.user.entity.UserEntity;
import com.cosimba.dive.domain.user.repository.UserRepository;
import com.cosimba.dive.domain.user.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    public UserEntity register(UserEntity userEntity) {
        Optional<UserEntity> existingUser = userRepository.findByUserId(userEntity.getUserId());
        if (existingUser.isPresent()) { // 아이디 중복 체크
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));  // 비밀번호 암호화
        return userRepository.save(userEntity);
    }

    // 로그인
    public String login(String userId, String password) {
        Optional<UserEntity> user = userRepository.findByUserId(userId);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return jwtTokenProvider.createToken(user.get().getUserId(), user.get().getRole());
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }

    // 아이디 중복 체크
    public boolean isUserIdDuplicate(String userId) {
        return userRepository.findByUserId(userId).isPresent();
    }

    public Optional<UserEntity> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }
}
