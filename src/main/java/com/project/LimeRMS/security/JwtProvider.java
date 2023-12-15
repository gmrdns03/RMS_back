package com.project.LimeRMS.security;

import com.project.LimeRMS.mapper.UserMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${spring.security.jwt.token-validity-in-seconds}")
    private Long expireIn;
    @Value("${spring.security.jwt.secret}")
    private String secretKey;
//    private final UserDetailsService userDetailsService;
    private final UserMapper userMapper;

    @PostConstruct
    protected void afterPropertiesSet() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createJwt(String userEmail){
        Date now = new Date();
        Map<String, Object> claims = new HashMap<>(); //추가로 전달하고 싶은 정보는 claims에 담기
        claims.put("userEmail", userEmail);

        String accessToken = Jwts.builder()
                .setHeaderParam("type","jwt")
                .claims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+expireIn)) //3시간
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return accessToken;
    }

//    // JWT 토큰에서 인증 정보 조회
//    public Authentication getAuthentication(String token) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(Long.toString(this.getUserEmail(token)));
//        return new UsernamePasswordAuthenticationToken(userDetails, "",
//                userDetails.getAuthorities());
//    }
//
//    // 토큰에서 회원 정보 추출
//    public String getUserPk(String token) {
//        return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("userEmail").toString();
//    }
//
//    public Long getUserEmail(String token) {
//        return Long.parseLong(getUserPk(token));
//    }
//
//    // Request의 Header의 token 값 "X-AUTH-TOKEN" : "tokenValue"
//    public String resolveToken(HttpServletRequest request) {
//        return request.getHeader("X-AUTH-TOKEN");
//    }
//
//    // 토큰의 유효성&만료일자 확인
//    public boolean validateToken(String token) {
//        try {
//            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
//            return !claims.getBody().getExpiration().before(new Date());
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    public Long getExpiration(String jwtToken) {
//        // accessToken 남은 유효시간
//        Date expiration = Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(jwtToken).getBody().getExpiration();
//        // 현재 시간
//        Long now = new Date().getTime();
//        return (expiration.getTime() - now);
//    }



}
