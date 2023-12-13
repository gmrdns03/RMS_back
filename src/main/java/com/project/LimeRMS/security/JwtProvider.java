package com.project.LimeRMS.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${spring.security.jwt.token-validity-in-seconds}")
    private Long expireIn;

    @Value("${spring.security.jwt.secret}")
    private String secretKey;

    @PostConstruct
    protected void afterPropertiesSet() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createJwt(String userEmail){
        Date now = new Date();

        String accessToken = Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("userEmail",userEmail)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+expireIn)) //3시간
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return accessToken;
    }

}
