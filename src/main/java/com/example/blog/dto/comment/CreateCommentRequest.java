package com.example.blog.dto.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CreateCommentRequest {
    @NotEmpty
    @ApiModelProperty(name = "content", value = "게시글 content", example = "내용")
    private String content;

    @NotEmpty
    @ApiModelProperty(name = "post_id", value = "게시글 id", example = "7")
    private String post_id;
}
