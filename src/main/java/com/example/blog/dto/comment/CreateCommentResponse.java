package com.example.blog.dto.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateCommentResponse {
    @ApiModelProperty(name = "message", value = "게시물 등록 메시지", example = "게시물이 성공적으로 작성되었습니다.")
    private String message;
}
