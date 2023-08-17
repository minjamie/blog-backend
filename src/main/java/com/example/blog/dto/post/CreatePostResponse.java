package com.example.blog.dto.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePostResponse {
    @ApiModelProperty(name = "message", value = "게시물 등록 메시지", example = "게시물이 성공적으로 작성되었습니다.")
    private String message;
}
