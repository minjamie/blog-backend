package com.example.blog.repository.like;

import com.example.blog.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostLIkeRepository extends JpaRepository<PostLike, Integer> {

    @Query(value =
            "SELECT pl " +
                    "FROM PostLike pl " +
                    "INNER JOIN pl.like l " +
                    "WHERE l.user.id = :userId " +
                    "AND pl.post.id = :postId")
    PostLike findPostLike(@Param("userId")Integer userId, @Param("postId")Integer postId);
}
