package com.bookstore.common.service;

import com.bookstore.common.model.Category;
import com.bookstore.modules.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    Category getById(Integer id);
    List<CategoryDto> getAll();
    List<CategoryDto> retrieveCategoriesByParentId(Integer parentId);
}
