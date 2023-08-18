package com.example.blog.dto.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteCommentResponse {
    @ApiModelProperty(name = "message", value = "댓글 삭제 메시지", example = "댓글이 성공적으로 삭제되었습니다.")
    private String message;
}
