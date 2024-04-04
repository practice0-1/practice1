package com.blog9.service.impl;

import com.blog9.entity.Post;
import com.blog9.exception.ResourceNotFoundException;
import com.blog9.payload.PostDto;
import com.blog9.payload.PostResponse;
import com.blog9.repository.PostRepository;
import com.blog9.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;


    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post newPost = postRepository.save(post);

        PostDto dto = new PostDto();
        dto.setId(newPost.getId());
        dto.setTitle(newPost.getTitle());
        dto.setContent(newPost.getContent());
        dto.setDescription(newPost.getDescription());
        return dto;



    }

   

    @Override
    public PostDto getPostByID(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post not found with ID "+id)
        );
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post not found by this id " + id)
        );
        Post newPost = mapToEntity(postDto);
        newPost.setId(id);
        Post updatedPost = postRepository.save(newPost);

        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post not found by id " + id));
        postRepository.deleteById(id);

    }

    @Override
    public PostResponse getPost(int pageNo, int pageSize, String pageSort, String pageDir) {

        Sort sort = pageDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(pageSort).ascending() : Sort.by(pageSort).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);

        Page<Post> pagePost = postRepository.findAll(pageable);
        List<Post> posts = pagePost.getContent();

        java.util.List<PostDto> dto = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setPostDto(dto);
        postResponse.setPageNo(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setLast(pagePost.isLast());
        postResponse.setTotalPages(pagePost.getTotalPages());
        return postResponse;
    }


    PostDto mapToDto(Post post){
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setDescription(post.getDescription());
        return dto;


    }
    Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        return post;

    }
}
