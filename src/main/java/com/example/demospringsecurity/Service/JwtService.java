package com.example.demospringsecurity.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    // tạo token
    // khởi tạo đối tượng JwtBuilder để build Jwt
    // thêm các thông tin muốn lữu trữ trong token vào jwt
    // thiết lập chủ đề của Jwtở đây là username
    // thiết lập thời gian khởi tạo và thời gian sống cho Jwt
    // ký Token sử dụng thuật toán ES256
    // tạo chuỗi Jwt token đã được ký số
    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*2))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }
    /* có 3 loại thuật toán phổ bin để ký Jwt token:
    HMAC (Hash-based Message Authentication Code)
    RSA (Rivest–Shamir–Adleman)
    ECDSA (Elliptic Curve Digital Signature Algorithm)
    ES256 là 1 loại thuộc ECDSA với tốc độ nhanh và an toàn cao
     */

    /*
    SECRET là một chuỗi base64 mã hóa khóa bí mật (secret key) dùng để ký token.
    Dùng Keys.hmacShaKeyFor() để convert mảng byte[] thành đối tượng SecretKey.
    SecretKey này sau đó sẽ được sử dụng trong hàm signWith() ở phía tạo token.
     */
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
