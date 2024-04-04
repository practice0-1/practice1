package com.blog9.service;

import com.blog9.payload.PostDto;
import com.blog9.payload.PostResponse;

import java.util.List;


public interface PostService {
    PostDto createPost(PostDto postDto);


    PostDto getPostByID(long id);

    PostDto updatePost(long id, PostDto postDto);

    void deletePostById(long id);

    PostResponse getPost(int pageNo, int pageSize, String pageSort, String pageDir);
}
