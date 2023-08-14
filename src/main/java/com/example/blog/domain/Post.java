package com.example.blog.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class Post extends BaseTime {

    @Id
    @Column(name="post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = true)
    private String author;

    private String title;

    private String content;

    @Column(name = "is_deleted",columnDefinition = "boolean default false")
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    @OneToMany(mappedBy = "post")
//    private List<Comment> comment;
//
//    @OneToMany(mappedBy = "post")
//    private List<Like> like;
}
