package com.microservice1.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private static final String SECRET_KEY="my_super_secret_key";
    private static final long EXPIRATION_TIME=86400000;
    //    Decrypting/decoding token
    public String validateTokenAndRetrieveSubject(String token){
        return JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build()
                .verify(token)
                .getSubject();
    }
}
