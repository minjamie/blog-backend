package com.example.blog.dto.comment;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GetCommentsResponse {
    List<GetCommentDto> comments;
}
