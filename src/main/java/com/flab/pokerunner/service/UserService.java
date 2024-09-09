package com.flab.pokerunner.service;

import com.flab.pokerunner.domain.dto.UserLoginRequestDTO;
import com.flab.pokerunner.domain.dto.UuidRequestDTO;
import com.flab.pokerunner.domain.entity.User;
import com.flab.pokerunner.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public boolean checkUuid(UuidRequestDTO uuidRequestDTO) {

        return userRepository.existsByUuid(uuidRequestDTO.getUuid());
    }

    public boolean login(UserLoginRequestDTO userLoginRequestDTO) {
        if (userRepository.existsByUuid(userLoginRequestDTO.getUuid())) {
            log.info("User with UUID {} already exists.", userLoginRequestDTO.getUuid());
            return false;
        }

        User user = User.builder()
                .uuid(userLoginRequestDTO.getUuid())
                .nickname(userLoginRequestDTO.getNickname())
                .address(userLoginRequestDTO.getAddress())
                .build();

        userRepository.save(user);
        return true;
    }
}
