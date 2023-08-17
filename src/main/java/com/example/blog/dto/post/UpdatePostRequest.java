package com.example.blog.dto.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class UpdatePostRequest {
    @ApiModelProperty(name = "title", value = "게시글 제목", example = "제목")
    private String title;

    @ApiModelProperty(name = "content", value = "게시글 content", example = "내용")
    private String content;
}
