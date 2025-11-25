package com.microservice1.client;

import com.microservice1.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name="AUTHPROJECT",
        url="http://localhost:8080/api/v1/auth"
        )
public interface UserClient {
    @GetMapping("/getUser")
    public User getUserByName(@RequestParam("username") String username, @RequestHeader("Authorization")String token);
}
