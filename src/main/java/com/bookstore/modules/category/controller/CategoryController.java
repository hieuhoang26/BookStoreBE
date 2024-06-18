package com.bookstore.modules.category.controller;

import com.bookstore.common.dto.response.ResponseData;
import com.bookstore.common.service.CategoryService;
import com.bookstore.common.util.Uri;
import com.bookstore.modules.category.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    final CategoryService categoryService;

    @GetMapping(value = Uri.CATEGORY)
    public ResponseData GetAllCategory(){
        List<CategoryDto> rs = categoryService.getAll();
        return new ResponseData(HttpStatus.OK.value(),"List category",rs);
    }
}
