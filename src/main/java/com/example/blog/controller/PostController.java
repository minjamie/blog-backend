package com.example.blog.controller;

import com.example.blog.domain.User;
import com.example.blog.dto.post.*;
import com.example.blog.properties.JwtProperties;
import com.example.blog.service.auth.JwtTokenProvider;
import com.example.blog.service.post.PostService;
import com.example.blog.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @ApiOperation("전체 게시글 조회")
    @GetMapping("/posts")
    public ResponseEntity<GetPostsResponse> getPosts(){
        GetPostsResponse response = new GetPostsResponse();
        response.setPosts(postService.getAllPost());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @ApiOperation("이메일로 나의 게시글 조회")
    @GetMapping("/posts/search")
    public ResponseEntity<GetPostsResponse> getPostsByAuthorEmail(
            @RequestParam String authorEmail
    ){
        GetPostsResponse response = new GetPostsResponse();
        response.setPosts(postService.getAllPostByEmail(authorEmail));
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @ApiOperation("게시글 작성")
    @PostMapping("/post")
    public ResponseEntity<CreatePostResponse> createPost(
            @RequestBody CreatePostRequest addPostRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            String token = httpServletRequest.getHeader(JwtProperties.HEADER_STRING);

            if(jwtTokenProvider.validateToken(token)){
                String email = jwtTokenProvider.getEmail(token);
                User user = userService.findUserByEmail(email);
                CreatePostResponse response = postService.createPost(user, email, addPostRequest);
                return new ResponseEntity(response, HttpStatus.CREATED);
            }
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @ApiOperation("게시글 수정")
    @PutMapping("/post/{post_id}")
    public ResponseEntity<UpdatePostResponse> updatePost(
            @PathVariable String post_id,
            @RequestBody UpdatePostRequest updatePostRequest,
            BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            String token = httpServletRequest.getHeader(JwtProperties.HEADER_STRING);

            if(jwtTokenProvider.validateToken(token)){
                String userId = jwtTokenProvider.getUserId(token);
                UpdatePostResponse response = postService.updatePost(userId, post_id, updatePostRequest);
                if(response.getMessage() == "해당 게시글은 존재하지 않습니다.") return new ResponseEntity(response, HttpStatus.NOT_FOUND);
                if(response.getMessage() == "해당 게시글은 본인이 작성한 게시글이 아닙니다.") return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
                if(response.getMessage() == "게시글이 성공적으로 수정되었습니다.") return new ResponseEntity(response, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

    }

    @ApiOperation("게시글 삭제")
    @DeleteMapping("/post/{post_id}")
    public ResponseEntity<DeletePostResponse> deletePost(
            @PathVariable String post_id
    ){
            String token = httpServletRequest.getHeader(JwtProperties.HEADER_STRING);

            if(jwtTokenProvider.validateToken(token)){
                String userId = jwtTokenProvider.getUserId(token);
                DeletePostResponse response = postService.deletePost(userId, post_id);
                if(response.getMessage() == "해당 게시글은 존재하지 않습니다.") return new ResponseEntity(response, HttpStatus.NOT_FOUND);
                if(response.getMessage() == "해당 게시글은 본인이 작성한 게시글이 아닙니다.") return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
                if(response.getMessage() == "게시물이 성공적으로 삭제되었습니다.") return new ResponseEntity(response, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}
