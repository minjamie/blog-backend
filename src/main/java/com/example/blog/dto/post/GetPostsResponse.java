package com.example.blog.dto.post;

import com.example.blog.domain.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GetPostsResponse {
    private List<GetPost> posts;
}
