package com.blog9.controller;

import com.blog9.payload.PostDto;

import com.blog9.payload.PostResponse;
import com.blog9.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //http://localhost:8080/api/posts
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

      PostDto dto =  postService.createPost(postDto);
      return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
        PostDto dto = postService.getPostByID(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);

    }
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,
                                              @PathVariable("id") long id){
        PostDto dto = postService.updatePost(id, postDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostByID(@PathVariable("id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("post is deleted",HttpStatus.OK);


    }
    //http://localhost:8080/api/posts?pageNo=0&pageSize=2
    @GetMapping
    public PostResponse getPost(
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(value ="pageSize", defaultValue = "3",required = false)int pageSize,
            @RequestParam(value = "pageSort",defaultValue = "id",required = false) String pageSort,
            @RequestParam(value="pageDir",defaultValue = "id",required = false)String pageDir
    ){
        PostResponse postResponse = postService.getPost(pageNo,pageSize,pageSort,pageDir);
       return postResponse;

    }
}
