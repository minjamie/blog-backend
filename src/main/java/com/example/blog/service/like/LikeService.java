package com.example.blog.service.like;

import com.example.blog.domain.Like;
import com.example.blog.domain.Post;
import com.example.blog.domain.PostLike;
import com.example.blog.domain.User;
import com.example.blog.dto.post.CreatePostResponse;
import com.example.blog.repository.like.LikeRepository;
import com.example.blog.repository.like.PostLIkeRepository;
import com.example.blog.repository.post.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class LikeService {
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final PostLIkeRepository postLIkeRepository;

    @Transactional
    public CreatePostResponse likePost(User user, String postId) {
        Post post = postRepository.findById(Integer.valueOf(postId)).orElse(null);
        if(post == null){
            return new CreatePostResponse("해당 게시글은 존재하지 않습니다.");
        }

        PostLike postLike = postLIkeRepository.findPostLike(user.getUserId(), Integer.valueOf(postId));

        if(postLike == null){
            Like like = new Like(
                    true,
                    user
            );
            likeRepository.save(like);
            PostLike createPostLike = PostLike.builder().post(post).like(like).build();
            postLIkeRepository.save(createPostLike);
            return new CreatePostResponse("좋아요가 등록되었습니다.");
        } else {
            if(!postLike.getLike().getIsLike()){
                postLike.getLike().setIsLike(true);
                postLike.setLike(postLike.getLike());
            } else {
                postLike.getLike().setIsLike(false);
                postLike.setLike(postLike.getLike());
            }
            postLIkeRepository.save(postLike);
            return new CreatePostResponse("좋아요가 상태가 변경되었습니다.");
        }
    }
}
