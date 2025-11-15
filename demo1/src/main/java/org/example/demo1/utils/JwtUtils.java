package org.example.demo1.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    // 从配置文件中读取密钥，或者使用默认值
    @Value("${jwt.secret:your-default-secret-key-at-least-32-chars}")
    private static String secret;

    // 生成密钥
    private static SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * 解析JWT token
     */
    public static Claims parseJwt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 创建JWT token（可选，用于登录时生成token）
     */
    public String createJwt(String subject, long expirationMs) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * 验证token是否有效
     */
    public boolean validateToken(String token) {
        try {
            parseJwt(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}