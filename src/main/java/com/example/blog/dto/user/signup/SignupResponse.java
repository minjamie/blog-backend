package com.example.blog.dto.user.signup;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupResponse {
    @ApiModelProperty(name = "message", value = "회원가입 메시지", example = "회원가입이 완료되었습니다.")
    private String message;
}
