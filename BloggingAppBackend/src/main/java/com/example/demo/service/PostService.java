package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Post;
import com.example.demo.exception.CategoryException;
import com.example.demo.exception.PostException;
import com.example.demo.exception.UserException;
import com.example.demo.payload.PostDTO;
import com.example.demo.payload.PostResponse;

public interface PostService {
	
	public PostDTO createPost(Post post,Integer userId,Integer categoryId) throws UserException,CategoryException;

	public PostDTO updatePost(Post post, Integer postId)throws PostException;
	
	public PostDTO getPostById(Integer postId) throws PostException;
	
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);

	public String deletePost(Integer postId) throws PostException;

	public List<PostDTO> getPostsByCategory(Integer categoryId) throws CategoryException,PostException;
	
	public List<PostDTO> getPostsByUser(Integer userId) throws UserException,PostException;
	
	public List<PostDTO> searchPosts(String keyword);

}
