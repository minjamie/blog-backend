package com.example.blog.repository.post;

import com.example.blog.domain.Post;
import com.example.blog.dto.post.GetPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Integer> {

    @Query("SELECT new com.example.blog.dto.post.GetPost(p.id, p.title, p.content, u.email AS author, p.createdDate AS created_at) " +
            "FROM Post p INNER JOIN p.user u")
    List<GetPost> findAllByAnonymous();

    @Query("SELECT new com.example.blog.dto.post.GetPost(p.id, p.content, p.title, u.email AS author, p.createdDate AS created_at) FROM Post p " +
            "INNER JOIN p.user u " +
            "WHERE u.email = :email")
    List<GetPost> findAllByEmail(String email);

    @Query("SELECT new com.example.blog.dto.post.GetPost(p.id, p.title, p.content, u.email AS author, p.createdDate AS created_at) " +
            "FROM Post p INNER JOIN p.user u " +
            "WHERE p.user.userId = :userId AND p.postId =:postId")
    GetPost findByUserIdAndPostId(Integer userId, @Param("postId")  Integer postId);
}
