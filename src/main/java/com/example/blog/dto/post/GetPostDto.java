package com.example.blog.dto.post;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetPostDto {
    private Integer id;
    private String content;
    private String title;
    private String author;
    private String created_at;
}
