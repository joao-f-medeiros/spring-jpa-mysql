package com.javaee.springjpamysql.api.v1.mapper;

import com.javaee.springjpamysql.api.v1.model.CategoryDTO;
import com.javaee.springjpamysql.domain.Category;

public class CategoryMapper {

	public CategoryDTO categoryToCategoryDTO(Category category) {
		final CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setDescription(category.getDescription());
        return categoryDTO;
	}

    public Category categoryDTOToCategory(CategoryDTO categoryDTO) {
    	final Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setDescription(categoryDTO.getDescription());
        return category;
    }
}
