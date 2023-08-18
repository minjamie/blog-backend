package com.example.blog.controller;

import com.example.blog.domain.User;
import com.example.blog.dto.like.LikePostResponse;
import com.example.blog.dto.post.CreatePostResponse;
import com.example.blog.properties.JwtProperties;
import com.example.blog.service.auth.JwtTokenProvider;
import com.example.blog.service.like.LikeService;
import com.example.blog.service.post.PostService;
import com.example.blog.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Api(tags = {"좋아요 관련 Controller"})
public class LikeController {

    private final UserService userService;
    private final LikeService likeService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @ApiOperation("게시글 좋아요")
    @PostMapping("/like/{post_id}")
    public ResponseEntity<LikePostResponse> likePost(
            @PathVariable String post_id
    ){

            String token = httpServletRequest.getHeader(JwtProperties.HEADER_STRING);

            if(jwtTokenProvider.validateToken(token)){
                String email = jwtTokenProvider.getEmail(token);
                User user = userService.findUserByEmail(email);
                CreatePostResponse response = likeService.likePost(user, post_id);
                return new ResponseEntity(response, HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
}
