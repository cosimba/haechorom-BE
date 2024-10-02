package com.cosimba.dive.domain.user.service;

import com.cosimba.dive.domain.user.entity.UserEntity;
import com.cosimba.dive.domain.user.repository.UserRepository;
import com.cosimba.dive.domain.user.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<UserEntity> userEntityOptional = userRepository.findByUserId(userId);

        if (userEntityOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with userId: " + userId);
        }

        UserEntity userEntity = userEntityOptional.get();

        // UserDetails 를 반환 (필요에 따라 CustomUserDetails 를 만들어 반환할 수도 있음)
        return org.springframework.security.core.userdetails.User.withUsername(userEntity.getUserId())
                .password(userEntity.getPassword())
                .authorities("ROLE_USER") // 역할을 추가
                .build();
    }
}
