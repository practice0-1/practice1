package com.blog9.controller;

import com.blog9.payload.CommentDto;
import com.blog9.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("posts/{postId}/comment")
     public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
                                                     @RequestBody CommentDto commentDto){

        CommentDto cDto = commentService.createComment(postId,commentDto);
        return new ResponseEntity<>(cDto, HttpStatus.CREATED);

     }
     @GetMapping("/post/{postId}/comment/{commentId}")
    public CommentDto getCommentById(@PathVariable (value="postId") long postId,
                                           @PathVariable(value="commentId") long commentId){
         CommentDto dto = commentService.getCommentById(postId, commentId);
         return  dto;


     }
}
