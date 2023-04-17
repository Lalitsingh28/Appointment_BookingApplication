package com.example.demo.service;

import com.example.demo.entity.Comment;
import com.example.demo.exception.ApiException;
import com.example.demo.exception.PostException;
import com.example.demo.payload.CommentDTO;

public interface CommentService {
	
	public CommentDTO createComment(Comment comment, Integer postId) throws PostException;

	public String deleteComment(Integer commentId) throws ApiException;

}
