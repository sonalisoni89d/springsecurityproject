package com.auth2.controller;

import com.auth2.dto.APIResponse;
import com.auth2.dto.UserDto;
import com.auth2.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<APIResponse<String>> register(@RequestBody UserDto userDto){
        APIResponse<String> save = authService.register(userDto);
        return new ResponseEntity<>(save, HttpStatusCode.valueOf(save.getStatus()));
    }
}
