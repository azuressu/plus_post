package com.repeat.jwt;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    // Header Header 값
    private static final String AUTHORIZATION_HEADER = "Authorization";

    // JWT 만료 시간
    private static final Long TOKEN_TIME = 60 * 60 * 100L;

    // JWT 식별자
    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    /* 의존성 주입이 이루어진 후, 초기화를 수행하는 메서드
     * 이 메서드는 다른 리소스에서 호출되지 않는다고 해도 수행됨
     * Bean LifeCycle에서 오직 한 번만 수행됨을 보장할 수 있음
     */
    @PostConstruct
    public void init() {
        // secretKey를 key로 Decoding
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // JWT 만들기
    public String createToken(String username) {
        Date date = new Date();

        String token = BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)                                  // 사용자 식별자 값 (사용자이름)
                        .setExpiration(new Date(date.getTime()+ TOKEN_TIME))   // JWT 만료 시간
                        .setIssuedAt(date)                                     // JWT 발급 시간
                        .signWith(key, signatureAlgorithm)                     // JWT 암호화 알고리즘
                        .compact();
        return token;
    }

    // JWT 쿠키에 넣기
    public void addJwtToCookie(String token, HttpServletResponse response) {
        try {
            // Cookie Value에는 공백이 불가능하므로 Encoding 진행
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20");

            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token); // Name - Value
            cookie.setPath("/");                                     // 해당 쿠키를 보낼 서버의 URL 하위 집합을 지정하는 방법
                                                                     // "/" - 해당 도메인에 속한 모든 URL 요청에 대해 전송
                                                                     // (URL에 특정 경로가 있던 없던 도메인에 속한 모든 요청에 대해 쿠키가 전송됨)

            response.addCookie(cookie);                              // response 객체에 cookie 추가
        } catch (UnsupportedEncodingException ex) {
            log.error(ex.getMessage());
        }
    }

    // 쿠키에서 JWT 가져오기
    public String getTokenFromCooike (HttpServletRequest request) {
        Cookie [] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if (cookie.getName().equals(AUTHORIZATION_HEADER)) {
                    try {
                        // Encode되어 넘어간 Value를 다시 Decode 함
                        return URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException ex) {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    // JWT 자르기
    public String substringToken(String tokenValue) {
        if (!StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            log.error("토큰이 비어있습니다.");
            throw new NullPointerException("토큰이 비어있습니다.");
        }
        return tokenValue.substring(7);
    }

    // JWT 검증하기
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
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

    // JWT에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

    }
}
