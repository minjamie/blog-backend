package com.example.blog.repository.comment;

import com.example.blog.domain.Comment;
import com.example.blog.dto.comment.GetCommentDto;
import com.example.blog.dto.post.GetPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("SELECT new com.example.blog.dto.comment.GetCommentDto(c.id, c.content, u.email AS author, p.postId AS post_id, c.createdDate AS created_at) " +
            "FROM Comment c " +
            "JOIN c.user u ON u.userId = c.user.userId " +
            "JOIN c.post p ON p.postId = c.post.postId")
    List<GetCommentDto> findAllComment();
}
