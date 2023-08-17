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

    public Post(User user, String author,String title, String content) {
        this.user = user;
        this.author = author;
        this.title = title;
        this.content = content;
    }

    private String author;

    private String title;

    private String content;

    @Column(name = "is_deleted",columnDefinition = "boolean default false")
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comment;

    @OneToMany(mappedBy = "post")
    private List<Like> like;

    public void setTitleAndContent(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
