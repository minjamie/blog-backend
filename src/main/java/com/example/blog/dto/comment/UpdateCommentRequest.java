package com.example.blog.dto.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCommentRequest {
    @ApiModelProperty(name = "content", value = "댓글 content", example = "내용")
    private String content;
}
