package com.blog9.service;

import com.blog9.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    CommentDto getCommentById(long postId, long commentId);
}
