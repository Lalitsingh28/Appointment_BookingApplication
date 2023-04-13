package com.example.demo.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CategoryDTO {
	
	private Integer categoryId;
	
	@NotBlank
	@Size(min = 4,message = "Min 4 characters required for title")
	private String categoryTitle;

	@NotBlank
	private String categoryDescription;

}
