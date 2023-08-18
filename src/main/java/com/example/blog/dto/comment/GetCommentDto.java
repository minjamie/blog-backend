package com.example.blog.dto.comment;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCommentDto {
    private Integer id;
    private String content;
    private String author;
    private Integer post_id;
    private String created_at;
}
