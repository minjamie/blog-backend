package com.example.blog.dto.user.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    @ApiModelProperty(name = "message", value = "회원가입 메시지", example = "로그인이 성공적으로 완료되었습니다.")
    private String message;
}
