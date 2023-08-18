package com.example.blog.controller;

import com.example.blog.dto.user.login.LoginResponse;
import com.example.blog.dto.user.login.LoginRequest;
import com.example.blog.dto.user.logout.LogoutRequest;
import com.example.blog.dto.user.logout.LogoutResponse;
import com.example.blog.dto.user.signup.SignupRequest;
import com.example.blog.dto.user.signup.SignupResponse;
import com.example.blog.service.auth.AuthService;
import com.example.blog.service.auth.RefreshTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Api(tags = {"회원 관련 Controller"})
public class UserController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @ApiOperation("회원가입")
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> Signup(@RequestBody @Valid SignupRequest signUpRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            SignupResponse response = authService.signUp(signUpRequest);
            return new ResponseEntity(response, HttpStatus.CREATED);
        }
    }

    @ApiOperation("로그인")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> Login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse httpServletResponse, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            LoginResponse response = authService.login(loginRequest, httpServletResponse);
            if(response.getMessage() == "해당 Email에 유저를 발견할 수 없습니다.") return new ResponseEntity(response, HttpStatus.NOT_FOUND);
            if(response.getMessage() == "비밀번호가 틀렸습니다.") return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
            return new ResponseEntity(response, HttpStatus.OK);
        }
    }

    @ApiOperation("로그아웃")
    @DeleteMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(@RequestBody LogoutRequest logoutRequest){
        LogoutResponse response = refreshTokenService.deleteRefreshToken(logoutRequest.getRefreshToken());
        if(response.getMessage() == "로그아웃 실패했습니다.") return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
