package com.example.blog.dto.user.logout;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class LogoutRequest {
    @NotEmpty
    @ApiModelProperty(name = "refreshToken", value = "리프레쉬 토큰", example = "-")
    String refreshToken;
}

