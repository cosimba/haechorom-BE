package com.cosimba.dive.domain.user.service;

import com.cosimba.dive.domain.user.entity.UserEntity;
import com.cosimba.dive.domain.user.repository.UserRepository;
import com.cosimba.dive.domain.user.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public UserEntity register(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));  // 비밀번호 암호화
        return userRepository.save(userEntity);
    }

    public String login(String userId, String password) {
        Optional<UserEntity> user = userRepository.findByUserId(userId);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return jwtTokenProvider.createToken(user.get().getUserId(), user.get().getRole());
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<UserEntity> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    // 사용자 정보 업데이트 (비밀번호 변경에 사용)
    public UserEntity updateUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
}
