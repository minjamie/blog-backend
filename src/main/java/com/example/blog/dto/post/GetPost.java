package com.example.blog.dto.post;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetPost {
    private Integer id;
    private String content;
    private String title;
    private String author;
    private String created_at;
}
