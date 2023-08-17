package com.example.blog.service.post;

import com.example.blog.domain.Post;
import com.example.blog.domain.User;
import com.example.blog.dto.post.*;
import com.example.blog.repository.post.PostRepository;
import com.example.blog.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<GetPost> getAllPost() {
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

    public List<GetPost> getAllPostByEmail(String email) {
        return postRepository.findAllByEmail(email);
    }

    public UpdatePostResponse updatePost(String userId, String postId, UpdatePostRequest updatePostRequest) {
        Integer wroteUserId = Integer.valueOf(postId);
        Long findPostId = Long.valueOf(userId);

        Optional<Post> updatePost = postRepository.findByUserIdAndPostId(wroteUserId,findPostId);
       if(updatePost.isEmpty()){
           return new UpdatePostResponse("본인이 작성한 해당 게시글은 존재하지 않습니다.");
       } else {
           updatePost.get().setTitleAndContent(updatePostRequest.getTitle(), updatePostRequest.getContent());
           postRepository.save(updatePost.get());
           return new UpdatePostResponse("게시글이 성공적으로 수정되었습니다.");
       }
    }
}
