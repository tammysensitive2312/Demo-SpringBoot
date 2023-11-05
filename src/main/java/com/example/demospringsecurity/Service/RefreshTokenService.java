package com.example.demospringsecurity.Service;

import com.example.demospringsecurity.Entity.RefreshToken;
import com.example.demospringsecurity.Repository.RefreshTokenRepository;
import com.example.demospringsecurity.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private UserRepository userRepository;

    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findByName(username).get())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000)) // 10 phút
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /*
    xác thực và kiểm tra thời hạn của một refresh token.
    Các bước chính trong đoạn code:
    1. Nhận vào một đối tượng RefreshToken
    2. So sánh ngày hết hạn của token (token.getExpiryDate()) với thời gian hiện tại (Instant.now())
    3. Nếu ngày hết hạn nhỏ hơn thời gian hiện tại, nghĩa là token đã hết hạn:
            - Xóa token khỏi cơ sở dữ liệu (refreshTokenRepository.delete(token))
            - Ném ra exception với thông báo token đã hết hạn
    4. Nếu token chưa hết hạn, trả về chính token đó.
    Như vậy, hàm này sẽ kiểm tra xem một refresh token còn valid hay đã hết hạn,
    để có thể quyết định có cho phép refresh lấy access token mới hay không
    */
    public RefreshToken  verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }
}
