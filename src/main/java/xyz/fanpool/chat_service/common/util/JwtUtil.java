package xyz.fanpool.chat_service.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class JwtUtil {

    private static final String IDENTIFICATION_CLAIM = "identification";

    private final SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secret-key}") String secretKey) {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        this.secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public long getUserId(String token) {
        Claims claims = extractAllClaims(token);
        long userId;
        try {
            userId = Long.parseLong(claims.getSubject());
        } catch (NumberFormatException e) {
            userId = (long) claims.get(IDENTIFICATION_CLAIM);
        }
        return userId;
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
