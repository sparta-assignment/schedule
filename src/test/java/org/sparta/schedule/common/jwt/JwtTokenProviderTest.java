package org.sparta.schedule.common.jwt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sparta.schedule.entity.UserRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @DisplayName("jwt 토큰 생성 및 가져오기 테스트")
    @Test
    void createToken() {
        String username = "test@test.com";
        UserRoleEnum userRoleEnum = UserRoleEnum.USER;
        String token = jwtTokenProvider.createToken(username, userRoleEnum);

        System.out.println(token);

        Assertions.assertEquals(username, jwtTokenProvider.getUsername(token));
    }

    @DisplayName("토큰 검증 테스트")
    @Test
    void validateToken() {
        // 정상 토큰
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwiYXV0aCI6IlVTRVIiLCJleHAiOjE3MTY4ODkzNjIsImlhdCI6MTcxNjg4NTc2Mn0.b7VuH2bAOAtqNYoYiau0q0I3ZDdpfZw7pC6avoKzohTvNyGviG39RYir_RSIMSVjDgNLGQLhx5U20DQS5PQk9A";
        // 만료된 토큰
        String expToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwiYXV0aCI6IlVTRVIiLCJleHAiOjE3MTY4ODYxMjMsImlhdCI6MTcxNjg4NjEyMn0.jyO1FvBPUePXuo-ZKbiC9n2oS32VYAwkIUYWFV4XHYWbEiHuJ82n5n98nGbRrbZkRUfC0inLgr_W3ypl6Y9O4Q";
        // 잘못된 JWT 토큰
        String invalidToken = "1234";

        Assertions.assertEquals(true, jwtTokenProvider.validateToken(token));
        Assertions.assertEquals(false, jwtTokenProvider.validateToken(expToken));
        Assertions.assertEquals(false, jwtTokenProvider.validateToken(invalidToken));
    }
}