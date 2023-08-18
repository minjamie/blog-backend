package com.example.blog.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comments")
public class Comment extends BaseTime {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    private String author;

    private String content;

    @Column(name = "is_deleted",columnDefinition = "boolean default false")
    private Boolean isDeleted;

    @ManyToOne()
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(User user, Post post, String author, String content) {
        this.user = user;
        this.post = post;
        this.author = author;
        this.content = content;
    }
}
