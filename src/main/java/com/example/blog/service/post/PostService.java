package com.example.blog.service.post;

import com.example.blog.domain.Post;
import com.example.blog.domain.User;
import com.example.blog.dto.post.*;
import com.example.blog.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<GetPostDto> getAllPost() {
        return postRepository.findAllByAnonymous();
    }


    public CreatePostResponse createPost(User user, String email, CreatePostRequest createPostRequest) {
        Post post = new Post(
                user,
                email,
                createPostRequest.getTitle(),
                createPostRequest.getContent()
        );
       postRepository.save(post);
       return new CreatePostResponse("게시물이 성공적으로 작성되었습니다.");
    }

    public List<GetPostDto> getAllPostByEmail(String email) {
        return postRepository.findAllByEmail(email);
    }

    public UpdatePostResponse updatePost(String userId, String postId, UpdatePostRequest updatePostRequest) {
        Integer wroteUserId = Integer.valueOf(userId);
        Integer findPostId = Integer.valueOf(postId);

        Post post = postRepository.findById(findPostId).orElse(null);
        if(post == null){
            return new UpdatePostResponse("해당 게시글은 존재하지 않습니다.");
        }

        User user = post.getUser();
        if(user.getUserId() != wroteUserId){
            return new UpdatePostResponse("해당 게시글은 본인이 작성한 게시글이 아닙니다.");
        }
        post.setTitle(updatePostRequest.getTitle());
        post.setContent(updatePostRequest.getContent());
        postRepository.save(post);
        return new UpdatePostResponse("게시글이 성공적으로 수정되었습니다.");
    }

    public DeletePostResponse deletePost(String userId, String postId) {
        Integer wroteUserId = Integer.valueOf(userId);
        Integer findPostId = Integer.valueOf(postId);

        Post post = postRepository.findById(findPostId).orElse(null);
        if(post == null){
            return new DeletePostResponse("해당 게시글은 존재하지 않습니다.");
        }
        User user = post.getUser();
        if(user.getUserId() != wroteUserId){
            return new DeletePostResponse("해당 게시글은 본인이 작성한 게시글이 아닙니다.");
        }
            postRepository.deleteById(findPostId);
            return new DeletePostResponse("게시글이 성공적으로 삭제되었습니다.");
    }
}
