package com.example.blog.dto.user.logout;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
public class LogoutResponse {
    @ApiModelProperty(name = "message", value = "로그아웃 메시지", example = "로그아웃되었습니다.")
    private String message;
}

