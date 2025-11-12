package com.auth2.controller;

import com.auth2.dto.APIResponse;
import com.auth2.dto.ErrorDto;
import com.auth2.dto.LoginDto;
import com.auth2.dto.UserDto;
import com.auth2.service.AuthService;
import com.auth2.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager manager;
    private final JWTService jwtService;
    @PostMapping("/register")
    public ResponseEntity<APIResponse<String>> register(@RequestBody UserDto userDto){
        APIResponse<String> save = authService.register(userDto);
        return new ResponseEntity<>(save, HttpStatusCode.valueOf(save.getStatus()));
    }
//    http://localhost:8080/api/v1/auth/login
    @PostMapping("/login")
    public ResponseEntity<APIResponse<String>> loginCheck(@RequestBody LoginDto loginDto){
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword());
       try{
           Authentication authenticate = manager.authenticate(token);//toke->manager->provider
           if(authenticate.isAuthenticated()){
               String jwtToken=jwtService.generateToken(loginDto.getUsername(),
                       authenticate.getAuthorities().iterator().next().getAuthority());
               APIResponse<String> success=APIResponse.<String>builder()
                       .message("Login successful")
                       .status(200)
                       .data(jwtToken)
                       .build();
               return new ResponseEntity<>(success,HttpStatusCode.valueOf(success.getStatus()));
           }
       }catch (Exception e){
           e.printStackTrace();
       }

        APIResponse<String> failed=APIResponse.<String>builder()
                .message("Login failed")
                .status(500)
                .data("User is not logged in")
                .build();
        return new ResponseEntity<>(failed,HttpStatusCode.valueOf(failed.getStatus()));
    }
    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }
//    //http://localhost:8080/api/v1/auth/3
//    @DeleteMapping("/{id}")
//    public ResponseEntity<APIResponse<String>> deleteRecord(@PathVariable Long id){
//        authService.deleteRegistration(id);
//        APIResponse<String> delete=APIResponse.<String>builder()
//                .message("Deleted")
//                .status(200)
//                .data("Data deleted")
//                .build();
//        return new ResponseEntity<>(delete,HttpStatusCode.valueOf(delete.getStatus()));
//    }
}
