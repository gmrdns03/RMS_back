package com.project.LimeRMS.security;

import com.project.LimeRMS.mapper.UserMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.util.logging.Logger;
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

    @Value("${spring.security.jwt.token-validity-in-milliseconds}")
    private Long expireIn;
    @Value("${spring.security.jwt.secret}")
    private String secretKey;
    private static final Logger log = Logger.getLogger(JwtProvider.class.getName());
    private final UserDetailsService userDetailsService;
    private final UserMapper userMapper;

    @PostConstruct
    protected void afterPropertiesSet() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String generateAccessToken(String userNm, String userEmail, Integer priority, String authNm, Integer userId){
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expireIn);
        Map<String, Object> claims = new HashMap<>(); //추가로 전달하고 싶은 정보는 claims에 담기
        claims.put("userEmail", userEmail);
        claims.put("userNm", userNm);
        claims.put("priority", priority);
        claims.put("authNm", authNm);
        claims.put("userId", userId);


        String accessToken = Jwts.builder()
                .setHeaderParam("type","AccessToken")
                .claims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration) //3시간
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return accessToken;
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "",
            userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("userEmail").toString();
    }

    public String getUserEmail(String token) {
        return getUserPk(token);
    }

    // Request의 Header의 token 값 "AccessToken" : "tokenValue"
    public String resolveToken(HttpServletRequest request) {
//        System.out.println("request: " + request);
        return request.getHeader("AccessToken");
    }

    // 토큰의 유효성 확인
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.info("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.info("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.info("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.info("JWT claims string is empty.");
        } catch (NullPointerException ex){
            log.info("JWT RefreshToken is empty");
        }
        return false;
    }
}
