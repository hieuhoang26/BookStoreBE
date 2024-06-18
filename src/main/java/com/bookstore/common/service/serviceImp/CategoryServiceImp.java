package com.bookstore.common.service.serviceImp;

import com.bookstore.common.model.Category;
import com.bookstore.common.repository.CategoryRepository;
import com.bookstore.common.service.CategoryService;
import com.bookstore.modules.category.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {
    final CategoryRepository categoryRepository;
    @Override
    public Category getById(Integer id) {
        return categoryRepository.findById(Long.valueOf(id)).orElse(null) ;
    }

    @Override
    public List<CategoryDto> getAll() {
        List<Category> categories = categoryRepository.findAllParentCategory();
        List<CategoryDto> rs = categories.stream().map(category -> {
             return CategoryDto.builder()
                     .id(category.getId())
                     .name(category.getCategoryName())
                     .parentId(category.getParentId())
                     .build();
        }).toList();
        rs.forEach(category -> {
            category.setSubcategories(
                retrieveCategoriesByParentId(Math.toIntExact(category.getId()))
            );
        });
        return rs;
    }

    @Override
    public List<CategoryDto> retrieveCategoriesByParentId(Integer parentId) {
        List<Category> categories =  categoryRepository.findCategoriesByParentId(parentId);
        return categories.stream().map(category -> CategoryDto.builder()
                .id(category.getId())
                .parentId(category.getParentId())
                .name(category.getCategoryName())
                .build()).toList();
    }
}
