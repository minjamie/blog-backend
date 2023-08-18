package com.example.blog.dto.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeletePostResponse{
    @ApiModelProperty(name = "message", value = "게시물 삭제 메시지", example = "게시글이 성공적으로 삭제되었습니다.")
    private String message;

}
