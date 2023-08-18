package com.example.blog.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "likes")
public class Like extends BaseTime {
    @Id
    @Column(name = "like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @Column(name = "is_like", columnDefinition = "boolean default false")
    private Boolean isLike;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Like(Boolean isLike, User user) {
        this.isLike = isLike;
        this.user = user;
    }
}
