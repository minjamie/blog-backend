package com.example.blog.service.comment;


import com.example.blog.domain.Comment;
import com.example.blog.domain.Post;
import com.example.blog.domain.User;
import com.example.blog.dto.comment.*;
import com.example.blog.dto.post.UpdatePostResponse;
import com.example.blog.repository.comment.CommentRepository;
import com.example.blog.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    public List<GetCommentDto> getAllComments() {
        return commentRepository.findAllComment();

    }

    public CreateCommentResponse createComment(User user, String email, CreateCommentRequest createCommentRequest) {
        Integer findPostId = Integer.valueOf(createCommentRequest.getPost_id());
        Post post = postRepository.findById(findPostId).orElse(null);

        if(post == null){
            return new CreateCommentResponse("해당 게시글은 존재하지 않습니다.");
        }
        Comment comment = new Comment(
                user,
                post,
                email,
                createCommentRequest.getContent()
        );
        commentRepository.save(comment);
        return new CreateCommentResponse("게시물이 성공적으로 작성되었습니다.");
    }

    public UpdatePostResponse updateComment(String userId, String commentId, UpdateCommentRequest updateCommentRequest) {
        Integer wroteUserId = Integer.valueOf(userId);
        Integer findCommentId = Integer.valueOf(commentId);

        Comment comment = commentRepository.findById(findCommentId).orElse(null);
        if(comment == null){
            return new UpdatePostResponse("해당 댓글은 존재하지 않습니다.");
        }

        User user = comment.getUser();
        if(user.getUserId() != wroteUserId){
            return new UpdatePostResponse("해당 댓글은 본인이 작성한 게시글이 아닙니다.");
        }
        comment.setContent(updateCommentRequest.getContent());
        commentRepository.save(comment);
        return new UpdatePostResponse("댓글이 성공적으로 수정되었습니다.");
    }

    public DeleteCommentResponse deleteComment(String userId, String commentId) {
        Integer wroteUserId = Integer.valueOf(userId);
        Integer findCommentId = Integer.valueOf(commentId);

        Comment comment =  commentRepository.findById(findCommentId).orElse(null);
        if(comment == null){
            return new DeleteCommentResponse("해당 댓글은 존재하지 않습니다.");
        }
        User user = comment.getUser();
        if(user.getUserId() != wroteUserId){
            return new DeleteCommentResponse("해당 댓글은 본인이 작성한 게시글이 아닙니다.");
        }
        commentRepository.deleteById(findCommentId);
        return new DeleteCommentResponse("댓글이 성공적으로 삭제되었습니다.");
    }
}
