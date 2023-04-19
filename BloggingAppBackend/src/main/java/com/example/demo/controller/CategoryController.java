package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Category;
import com.example.demo.exception.CategoryException;
import com.example.demo.payload.CategoryDTO;
import com.example.demo.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	// create

	@PostMapping("/")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody Category category) {
		CategoryDTO createCategory = categoryService.createCategory(category);
		return new ResponseEntity<CategoryDTO>(createCategory, HttpStatus.CREATED);
	}

	// update

	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody Category category,
			@PathVariable Integer catId) throws CategoryException {
		CategoryDTO updatedCategory = categoryService.updateCategory(category, catId);
		return new ResponseEntity<CategoryDTO>(updatedCategory, HttpStatus.OK);
	}

	// delete

	@DeleteMapping("/{catId}")
	public ResponseEntity<String> deleteCategory(@PathVariable Integer catId) throws CategoryException {
		categoryService.deleteCategory(catId);
		return new ResponseEntity<>("category is deleted successfully !!", HttpStatus.OK);
	}
	// get

	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDTO> getCategory(@PathVariable Integer catId) throws CategoryException {

		CategoryDTO categoryDto = categoryService.getCategory(catId);

		return new ResponseEntity<CategoryDTO>(categoryDto, HttpStatus.OK);

	}

	// get all
	@GetMapping("/")
	public ResponseEntity<List<CategoryDTO>> getCategories() throws CategoryException {
		List<CategoryDTO> categories = categoryService.getAllCategories();
		return ResponseEntity.ok(categories);
	}

}
