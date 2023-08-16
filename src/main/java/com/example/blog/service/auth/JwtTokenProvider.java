package com.example.blog.service.auth;

import com.example.blog.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtTokenProvider {
    private final byte[] ACCESS_KEY = JwtProperties.SECRET_KEY.getBytes(StandardCharsets.UTF_8);
    private final byte[] REFRESH_KEY = JwtProperties.REFRESH_KEY.getBytes(StandardCharsets.UTF_8);

    public static final Long ACCESS_TOKEN_EXPIRE_COUNT = JwtProperties.ACCESS_TOKEN_EXPIRATION_TIME;
    public static final Long REFRESH_TOKEN_EXPIRE_COUNT = JwtProperties.REFRESH_TOKEN_EXPIRATION_TIME;

    public String getEmail(String token) {
        String[] tokenArr = token.split(" ");
        token = tokenArr[1];
        Claims claims = parseToken(token, ACCESS_KEY);
        return String.valueOf((String)claims.get("email"));
    }

    public String createToken(String email, Long expire, byte[] secretKey){
        Claims claims = Jwts.claims().setSubject(email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(new Date().getTime() + expire))
                .signWith(getSignInKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public String createAccessToken(String email) {
        return createToken(email,ACCESS_TOKEN_EXPIRE_COUNT, ACCESS_KEY);
    }

    public String createRefreshToken(String email) {
        return createToken(email, REFRESH_TOKEN_EXPIRE_COUNT, REFRESH_KEY);
    }

    public boolean validateToken(String jwtToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(getSignInKey(ACCESS_KEY))
                    .parseClaimsJws(jwtToken)
                    .getBody();
            Date now = new Date();
            return claims.getExpiration()
                    .after(now);
        } catch (Exception e) {
            return false;
        }
    }

    private Claims parseToken(String token, byte[] secretKey){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static Key getSignInKey(byte[] secretKey) {
        return Keys.hmacShaKeyFor(secretKey);
    }
}
