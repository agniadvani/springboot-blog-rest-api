package com.agniadvani.blog.springbootblogrestapi.service.impl;

import com.agniadvani.blog.springbootblogrestapi.entity.Post;
import com.agniadvani.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.agniadvani.blog.springbootblogrestapi.payload.PostDto;
import com.agniadvani.blog.springbootblogrestapi.payload.PostResponse;
import com.agniadvani.blog.springbootblogrestapi.repository.PostRepository;
import com.agniadvani.blog.springbootblogrestapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        //Convert DTO to entity

        Post post = mapToEntity(postDto);

        //Saving entity to DB
        Post newPost = postRepository.save(post);

        return mapToDto(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> postPage = postRepository.findAll(pageable);
        List<Post> postList = postPage.getContent();
        List<PostDto> content = postList.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLast(postPage.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getContent());
        post.setTitle(postDto.getTitle());
        postRepository.save(post);
        return mapToDto(post);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));
        postRepository.delete(post);
    }

    private PostDto mapToDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

    private Post mapToEntity(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }
}
