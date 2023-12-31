package com.agniadvani.blog.springbootblogrestapi.controller;


import com.agniadvani.blog.springbootblogrestapi.payload.CommentDto;
import com.agniadvani.blog.springbootblogrestapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentDto> createPost(@PathVariable long postId, @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable long postId, @PathVariable long commentId) {
        return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
    }

    @PutMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable long postId, @PathVariable long commentId, @RequestBody CommentDto commentRequest) {
        return new ResponseEntity<>(commentService.updateCommentById(postId, commentId, commentRequest), HttpStatus.OK);
    }

    @DeleteMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentById(@PathVariable long postId, @PathVariable long commentId) {
        commentService.deleteCommentById(postId, commentId);
        return new ResponseEntity<>("Comment Deleted Successfully", HttpStatus.OK);
    }
}
