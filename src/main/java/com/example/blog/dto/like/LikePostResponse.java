package com.example.blog.dto.like;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikePostResponse {
    @ApiModelProperty(name = "message", value = "게시글 좋아요 등록 메시지", example = "게시물에 좋아요 등록되었습니다")
    private String message;
}
