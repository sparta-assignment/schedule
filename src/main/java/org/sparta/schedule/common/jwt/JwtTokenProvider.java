package org.sparta.schedule.common.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.sparta.schedule.common.exception.CustomException;
import org.sparta.schedule.entity.UserRoleEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "JwtTokenProvider:: ")
@Component
public class JwtTokenProvider {
    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";
    @Value("${jwt.exp}")
    private long TOKEN_TIME;
    @Value("${jwt.secret}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private SecretKey key;

    // Bean이 초기화 됨과 동시에 호출해줌
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    /**
     * 토큰을 생성한다.
     *
     * @param username 유저 아이디
     * @param role 유저 권한
     * @param expMillis 만료시간(초)
     * @return 생성된 토큰
     */
    public String createToken(String username, UserRoleEnum role, long expMillis) {
        Date date = new Date();

        return Jwts.builder()
                .subject(username)
                .claim(AUTHORIZATION_KEY, role)
                .expiration(new Date(date.getTime() + expMillis * 1000L))
                .issuedAt(date)
                .signWith(key)  // 알고리즘 지정 안하면 key에 맞게 알아서 지정해줌
                .compact();
    }

    /**
     * 기본으로 설정된 토큰 만료시간을 갖는 토큰을 생성한다.
     *
     * @param username 유저 아이디
     * @param role 유저 권한
     * @return 생성된 토큰
     */
    public String createToken(String username, UserRoleEnum role) {
        return createToken(username, role, TOKEN_TIME);
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    public String getPreAccessTokenUsername(String token) {
        try {
             return this.getClaims(token).getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
        catch (Exception e) {
            throw new CustomException(HttpStatus.UNAUTHORIZED.value(), "잘못된 JWT 토큰입니다.");
        }
    }

    public String getUsername(String token) {
        Claims claims = this.getClaims(token);
        return claims.getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static String getTokenFromRequest(String token){
        final String TOKEN_BEARER_PREFIX = "Bearer ";
        if (token != null && token.startsWith(TOKEN_BEARER_PREFIX)){
            return token.substring(TOKEN_BEARER_PREFIX.length());
        }
        return null;
    }
}
