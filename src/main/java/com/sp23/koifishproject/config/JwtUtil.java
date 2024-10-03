package com.sp23.koifishproject.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    // Sử dụng SecretKey an toàn cho HS256
    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Tạo JWT từ email (subject) và role (claims)
    public String generateToken(String email, String role) {
        System.out.println("Generating JWT for email: " + email + " with role: " + role); // Log khi tạo JWT

        // Tạo claims chứa thông tin role
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)  // Thêm role vào claims
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Hết hạn sau 10 giờ
                .signWith(SECRET_KEY)
                .compact();
    }

    // Lấy email từ JWT
    public String extractEmail(String token) {
        System.out.println("Extracting email from token"); // Log khi trích xuất email từ JWT
        return extractClaim(token, Claims::getSubject);
    }

    // Lấy role từ JWT
    public String extractRole(String token) {
        System.out.println("Extracting role from token"); // Log khi trích xuất role từ JWT
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    // Kiểm tra token còn hiệu lực không
    public Boolean isTokenValid(String token, String email) {
        final String extractedEmail = extractEmail(token);
        System.out.println("Validating token for email: " + extractedEmail); // Log khi kiểm tra tính hợp lệ của JWT
        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
}
