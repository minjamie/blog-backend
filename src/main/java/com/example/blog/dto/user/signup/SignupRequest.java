package com.example.blog.dto.user.signup;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
public class SignupRequest {

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
            message = "이메일 형식을 맞춰야합니다")
    @ApiModelProperty(name = "email", value = "유저 email", example = "minjae2246@gmail.com")
    private String email;

    @NotEmpty
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{7,16}$",
            message = "비밀번호는 영문+숫자+특수문자를 포함한 8~20자여야 합니다")
    @ApiModelProperty(name = "password", value = "유저 password", example = "1234qwer@")
    private String password;

}
