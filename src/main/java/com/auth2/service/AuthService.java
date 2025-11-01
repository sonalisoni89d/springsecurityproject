package com.auth2.service;

import com.auth2.dto.APIResponse;
import com.auth2.dto.UserDto;
import com.auth2.entity.User;
import com.auth2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public APIResponse<String> register(UserDto userDto){
        if(userRepository.existsByEmail(userDto.getEmail())){
            return APIResponse.<String>builder()
                    .message("Registration failed")
                    .status(500)
                    .data("user already exists with this email")
                    .build();
        }
        if(userRepository.existsByUsername(userDto.getUsername())){
            return APIResponse.<String>builder()
                    .message("Registration failed")
                    .status(500)
                    .data("user already exists with this username")
                    .build();
        }
        User user=new User();
        BeanUtils.copyProperties(userDto,user);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        return APIResponse.<String>builder()
                .message("Registration done")
                .status(201)
                .data("success")
                .build();
    }
}
