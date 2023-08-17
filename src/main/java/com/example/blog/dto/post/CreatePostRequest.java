package com.example.blog.dto.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class CreatePostRequest {
    @NotEmpty
    @ApiModelProperty(name = "title", value = "게시글 제목", example = "제목")
    private String title;

    @NotEmpty
    @ApiModelProperty(name = "content", value = "게시글 content", example = "내용")
    private String content;
}
