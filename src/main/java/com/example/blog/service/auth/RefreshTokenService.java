package com.example.blog.service.auth;

import com.example.blog.domain.RefreshToken;
import com.example.blog.domain.User;
import com.example.blog.dto.user.logout.LogoutResponse;
import com.example.blog.repository.refreshToken.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public RefreshToken addRefreshToken(RefreshToken refreshToken){
        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public LogoutResponse deleteRefreshToken(String refreshToken) {
        Optional<RefreshToken> refreshTokenByValue = refreshTokenRepository.findByValue(refreshToken);
        if(refreshTokenByValue.isPresent()){
            refreshTokenRepository.deleteByValue(refreshToken);
            new LogoutResponse("로그아웃 성공했습니다.");
        }
        return new LogoutResponse("로그아웃 실패했습니다.");
    }

    @Transactional(readOnly = true)
    public Optional<RefreshToken> findRefreshToken(String refreshToken){
        return refreshTokenRepository.findByValue(refreshToken);
    }

    public Optional<RefreshToken> findRefreshTokenByUserId(User user){
        return refreshTokenRepository.findByUser(user);
    }
}
