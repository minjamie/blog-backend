package com.example.blog.service.auth;

import com.example.blog.domain.RefreshToken;
import com.example.blog.domain.Role;
import com.example.blog.domain.User;
import com.example.blog.dto.user.login.LoginRequest;
import com.example.blog.dto.user.login.LoginResponse;
import com.example.blog.dto.user.signup.SignupRequest;
import com.example.blog.dto.user.signup.SignupResponse;
import com.example.blog.properties.JwtProperties;
import com.example.blog.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public SignupResponse signUp(SignupRequest signUpRequest){

        // 이메일 중복 체크
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new SignupResponse("이미 가입된 아이디입니다.");
        } else {
            User user = User.builder()
                    .email(signUpRequest.getEmail())
                    .password(passwordEncoder.encode(signUpRequest.getPassword()))
                    .role(Role.USER)
                    .build();

            userRepository.save(user);
            return new SignupResponse("회원가입이 완료되었습니다.");
        }
    }

    public LoginResponse login(LoginRequest loginRequest, HttpServletResponse httpServletResponse) {
        String email = loginRequest.getEmail();

        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            return new LoginResponse("해당 Email에 유저를 발견할 수 없습니다.");
        }
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())){
            return new LoginResponse("비밀번호가 틀렸습니다.");
        }
            String accessToken = jwtTokenProvider.createAccessToken(user.get().getEmail());
            String refreshToken = jwtTokenProvider.createRefreshToken(user.get().getEmail());
            RefreshToken refreshTokenEntity = new RefreshToken();
            refreshTokenEntity.setValue(refreshToken);
            refreshTokenEntity.setUser(user.get());

            Optional<RefreshToken> refreshTokenByUserId = refreshTokenService.findRefreshTokenByUserId(user.get());
            if(refreshTokenByUserId.isEmpty()){
                refreshTokenService.addRefreshToken(refreshTokenEntity);
            }
            httpServletResponse.setHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + accessToken);
            httpServletResponse.setHeader(JwtProperties.REFRESH_HEADER_STRING, refreshTokenByUserId.isEmpty() ? JwtProperties.TOKEN_PREFIX + refreshToken : JwtProperties.TOKEN_PREFIX + refreshTokenByUserId.get().getValue());

        return new LoginResponse("로그인에 성공했습니다.");
    }
}
