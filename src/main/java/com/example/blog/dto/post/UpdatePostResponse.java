package com.example.blog.dto.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdatePostResponse {
    @ApiModelProperty(name = "message", value = "게시물 수정 메시지", example = "게시글이 성공적으로 수정되었습니다.")
    private String message;

}
