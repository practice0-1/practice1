package com.blog9.service.impl;

import com.blog9.entity.Comment;
import com.blog9.entity.Post;
import com.blog9.exception.ResourceNotFoundException;
import com.blog9.payload.CommentDto;
import com.blog9.repository.CommentRepository;
import com.blog9.repository.PostRepository;
import com.blog9.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepository;

    private CommentRepository commentRepository;

    private ModelMapper modelMapper;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
       Comment comment = maptoEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found by postId:" +postId)
        );

        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        CommentDto dto = maptoDto(savedComment);


        return dto;
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found by id" + postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment not found by Id" + commentId)
        );

       return maptoDto(comment);


    }

    private CommentDto maptoDto(Comment savedComment) {
        CommentDto dto = modelMapper.map(savedComment, CommentDto.class);
        return dto;
    }

    private Comment maptoEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;

    }
}
