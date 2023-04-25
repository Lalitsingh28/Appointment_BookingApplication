package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.AppConstant;
import com.example.demo.entity.Post;
import com.example.demo.exception.CategoryException;
import com.example.demo.exception.PostException;
import com.example.demo.exception.UserException;
import com.example.demo.payload.PostDTO;
import com.example.demo.payload.PostResponse;
import com.example.demo.service.FileService;
import com.example.demo.service.PostService;


@RestController
@RequestMapping("/post")
public class PostController {
	
	
	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;
	
	@Autowired
	private ModelMapper modelMapper;

	@Value("${project.image}")
	private String path;
	
	//	create

	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDTO> createPost(@RequestBody Post post, @PathVariable Integer userId,
			@PathVariable Integer categoryId) throws UserException, CategoryException {
		PostDTO createPost = postService.createPost(post, userId, categoryId);
		return new ResponseEntity<PostDTO>(createPost, HttpStatus.CREATED);
	}

	// get by user

	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable Integer userId) throws UserException, PostException {

		List<PostDTO> posts = postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);

	}

	// get by category

	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable Integer categoryId) throws CategoryException, PostException {

		List<PostDTO> posts = postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);

	}

	// get all posts

	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir) {

		PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}

	// get post details by id

	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable Integer postId) throws PostException {

		PostDTO PostDTO = postService.getPostById(postId);
		return new ResponseEntity<PostDTO>(PostDTO, HttpStatus.OK);

	}

	// delete post
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<String> deletePost(@PathVariable Integer postId) throws PostException {
		postService.deletePost(postId);
		return new ResponseEntity<String>("Post is successfully deleted !!", HttpStatus.OK);
	}

	// update post

	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody Post post, @PathVariable Integer postId) throws PostException {

		PostDTO updatePost = postService.updatePost(post, postId);
		return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);

	}

	// search
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDTO>> searchPostByTitle(@PathVariable("keywords") String keywords) {
		List<PostDTO> result = this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDTO>>(result, HttpStatus.OK);
	}

	// post image upload

	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDTO> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException, PostException {

		PostDTO PostDTO = postService.getPostById(postId);
		
		String fileName = fileService.uploadImage(path, image);
		PostDTO.setImageName(fileName);
		Post post = modelMapper.map(PostDTO, Post.class);
		PostDTO updatePost = postService.updatePost(post, postId);
		return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);

	}
	

}
