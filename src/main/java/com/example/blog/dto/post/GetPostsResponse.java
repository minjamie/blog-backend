package com.example.blog.dto.post;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GetPostsResponse {
    private List<GetPostDto> posts;
}
