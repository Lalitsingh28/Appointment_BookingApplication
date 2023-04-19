package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Comment;
import com.example.demo.exception.ApiException;
import com.example.demo.exception.PostException;
import com.example.demo.payload.CommentDTO;
import com.example.demo.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;

	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDTO> createComment(@RequestBody Comment comment, @PathVariable Integer postId) throws PostException {

		CommentDTO createComment = commentService.createComment(comment, postId);
		return new ResponseEntity<CommentDTO>(createComment, HttpStatus.CREATED);
	}

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Integer commentId) throws ApiException {

		commentService.deleteComment(commentId);

		return new ResponseEntity<>("Comment deleted successfully !!", HttpStatus.OK);
	}


}
