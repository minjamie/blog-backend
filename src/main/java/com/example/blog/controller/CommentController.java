package com.example.blog.controller;

import com.example.blog.domain.User;
import com.example.blog.dto.comment.*;
import com.example.blog.dto.post.UpdatePostResponse;
import com.example.blog.properties.JwtProperties;
import com.example.blog.service.auth.JwtTokenProvider;
import com.example.blog.service.comment.CommentService;
import com.example.blog.service.user.UserService;
import io.swagger.annotations.Api;
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
@Api(tags = {"댓글 관련 Controller"})
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private final HttpServletRequest httpServletRequest;


    @ApiOperation("전체 댓글 조회")
    @GetMapping("/comments")

    public ResponseEntity<GetCommentsResponse> getPosts() {
        GetCommentsResponse response = new GetCommentsResponse();
        response.setComments(commentService.getAllComments());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @ApiOperation("댓글 작성")
    @PostMapping("/comments")
    public ResponseEntity<CreateCommentResponse> createComment(
            @RequestBody CreateCommentRequest createCommentRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            String token = httpServletRequest.getHeader(JwtProperties.HEADER_STRING);

            if (jwtTokenProvider.validateToken(token)) {
                String email = jwtTokenProvider.getEmail(token);
                User user = userService.findUserByEmail(email);
                CreateCommentResponse response = commentService.createComment(user, email, createCommentRequest);
                return new ResponseEntity(response, HttpStatus.CREATED);
            }
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @ApiOperation("게시글 수정")
    @PutMapping("/comment/{comment_id}")
    public ResponseEntity<UpdateCommentResponse> updatePost(
            @PathVariable String comment_id,
            @RequestBody UpdateCommentRequest updateCommentRequest,
            BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            String token = httpServletRequest.getHeader(JwtProperties.HEADER_STRING);

            if(jwtTokenProvider.validateToken(token)){
                String userId = jwtTokenProvider.getUserId(token);
                UpdatePostResponse response = commentService.updateComment(userId, comment_id, updateCommentRequest);
                if(response.getMessage() == "해당 댓글은 존재하지 않습니다.") return new ResponseEntity(response, HttpStatus.NOT_FOUND);
                if(response.getMessage() == "해당 댓글은 본인이 작성한 게시글이 아닙니다.") return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
                if(response.getMessage() == "댓글이 성공적으로 수정되었습니다.") return new ResponseEntity(response, HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @ApiOperation("댓글 삭제")
    @DeleteMapping("/comment/{comment_id}")
    public ResponseEntity<DeleteCommentResponse> deletePost(
            @PathVariable String comment_id
    ){
        String token = httpServletRequest.getHeader(JwtProperties.HEADER_STRING);

        if(jwtTokenProvider.validateToken(token)){
            String userId = jwtTokenProvider.getUserId(token);
            DeleteCommentResponse response = commentService.deleteComment(userId, comment_id);
            if(response.getMessage() == "해당 댓글은 존재하지 않습니다.") return new ResponseEntity(response, HttpStatus.NOT_FOUND);
            if(response.getMessage() == "해당 댓글은 본인이 작성한 게시글이 아닙니다.") return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
            if(response.getMessage() == "댓글이 성공적으로 삭제되었습니다.") return new ResponseEntity(response, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}
